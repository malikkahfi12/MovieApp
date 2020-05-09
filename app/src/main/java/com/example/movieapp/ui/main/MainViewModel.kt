package com.example.movieapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.popular.PopularMovie
import com.example.movieapp.data.model.upcoming.UpcomingMovie
import com.example.movieapp.data.repository.UpcomingRepository
import com.example.movieapp.util.Constants.Companion.API_KEY
import com.example.movieapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    val upcomingRepository: UpcomingRepository
) : ViewModel() {
    val upcomingMovie : MutableLiveData<Resource<UpcomingMovie>> = MutableLiveData()
    val playNowMovie : MutableLiveData<Resource<PopularMovie>> = MutableLiveData()

    init {
        getUpcomingMovie(API_KEY)
        getNowPlayingMovie(API_KEY)
    }

    private fun getUpcomingMovie(apikey : String) = viewModelScope.launch {
        upcomingMovie.postValue(Resource.Loading())
        val response = upcomingRepository.getUpcomingMovies(apikey)
        upcomingMovie.postValue(handleUpcomingMovie(response))
    }

    private fun getNowPlayingMovie(apikey: String) = viewModelScope.launch {
        playNowMovie.postValue(Resource.Loading())
        val response = upcomingRepository.getNowPlayingMovies(apikey)
        playNowMovie.postValue(handlePopularMovie(response))
    }

    private fun handleUpcomingMovie(response: Response<UpcomingMovie>) : Resource<UpcomingMovie>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handlePopularMovie(response: Response<PopularMovie>) : Resource<PopularMovie>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}