package com.example.movieapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.data.repository.UpcomingRepository

class MovieViewModelProviderFactory(
    val upcomingRepository: UpcomingRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(upcomingRepository) as T
    }

}