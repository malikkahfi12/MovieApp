package com.example.movieapp.ui.main.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.details.Details
import com.example.movieapp.ui.main.MainActivity
import com.example.movieapp.ui.main.MainViewModel
import com.example.movieapp.util.Constants.Companion.API_KEY
import com.example.movieapp.util.Constants.Companion.BASE_URL_IMG
import com.example.movieapp.util.Resource
import kotlinx.android.synthetic.main.fragment_details.*
import java.text.SimpleDateFormat

class DetailsFragment : Fragment(R.layout.fragment_details) {

    lateinit var viewModel: MainViewModel
    val args : DetailsFragmentArgs by navArgs()
    val TAG = "DetailsFragments"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val article = args.upcoming
        Log.d(TAG, article.id.toString())
        viewModel.getDetailsMovies(API_KEY, article.id)
        viewModel.detailsMovie.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        Log.d(TAG, it.releaseDate)
                        showDataDetails(it)
                    }
                }
                is  Resource.Error -> {
                    response.message?.let {
                        Log.e(TAG, "An error occured : $it")
                    }
                }
                is  Resource.Loading -> {
                    showProgressbar()
                }
            }
        })
        img_poster_path.clipToOutline = true
    }

    private fun showDataDetails(it: Details) {
        // Backdrop
        Glide.with(this).load("https://image.tmdb.org/t/p/w500${it.backdropPath}").into(img_backdrop)
        // Poster
        Glide.with(this).load("$BASE_URL_IMG${it.posterPath}").into(img_poster_path)
        // title
        text_title_details.text = it.title
        // genres
        for (item in it.genres){
            text_trailers_details.text = "${item.name}, "
        }
        // Release date
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = formatter.parse(it.releaseDate)
        val desiredFormat = SimpleDateFormat("dd MMM yyyy").format(date)
        text_release_details.text = "Release, $desiredFormat"
        // Stroy Lines
        text_description_details.text = it.overview
    }

    private fun hideProgressBar(){
        progress_bar.visibility = View.GONE
        group_details.visibility = View.VISIBLE
    }

    private fun showProgressbar(){
        progress_bar.visibility = View.VISIBLE
        group_details.visibility = View.INVISIBLE
    }
}