package com.learetechno.movieapp.ui.main.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.learetechno.movieapp.R
import com.learetechno.movieapp.ui.main.MainActivity
import com.learetechno.movieapp.ui.main.MainViewModel
import com.learetechno.movieapp.util.Constants.Companion.API_KEY
import com.learetechno.movieapp.util.Resource
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {

    lateinit var viewModel: MainViewModel
    lateinit var searchAdapter: SearchAdapter
    val TAG = "SearchFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        var job : Job? = null
        edt_search.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(1000L)
                if (editable.toString().isNotEmpty()){
                    showProgressBar()
                    viewModel.getSearchMovies(API_KEY, 1, editable.toString())
                }
            }
        }

        searchAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("upcoming", it)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_detailsFragment,
                bundle
            )
        }

        viewModel.searchMovie.observe(viewLifecycleOwner, Observer { response ->
            when (response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        searchAdapter.differ.submitList(newsResponse.results)
                    }
                }
                is  Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Log.e(TAG, "An error occured : $it")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }



    private fun setupRecyclerView(){
        searchAdapter = SearchAdapter()
        rvSearchMovie.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun hideProgressBar(){
        progress_bar.visibility = View.GONE
        rvSearchMovie.visibility = View.VISIBLE
    }

    private fun showProgressBar(){
        progress_bar.visibility = View.VISIBLE
        rvSearchMovie.visibility = View.INVISIBLE
    }


}