package com.example.movieapp.ui.main.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.ui.main.MainActivity
import com.example.movieapp.ui.main.MainViewModel
import com.example.movieapp.util.Constants
import com.example.movieapp.util.Constants.Companion.API_KEY
import com.example.movieapp.util.Resource
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
                delay(500L)
                if (editable.toString().isNotEmpty()){
                    viewModel.getSearchMovies(API_KEY, 1, editable.toString())
                }
            }
        }

        viewModel.searchMovie.observe(viewLifecycleOwner, Observer { response ->
            when (response){
                is Resource.Success -> {

                    response.data?.let { newsResponse ->
                        searchAdapter.differ.submitList(newsResponse.results)
                    }
                }
                is  Resource.Error -> {

                    response.message?.let {
                        Log.e(TAG, "An error occured : $it")
                    }
                }
                is Resource.Loading -> {

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
}