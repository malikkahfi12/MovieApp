package com.learetechno.movieapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.learetechno.movieapp.data.repository.MovieRepository

class MovieViewModelProviderFactory(
    val movieRepository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(movieRepository) as T
    }

}