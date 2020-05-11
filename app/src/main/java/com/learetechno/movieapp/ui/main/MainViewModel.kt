package com.learetechno.movieapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learetechno.movieapp.data.model.details.Details
import com.learetechno.movieapp.data.model.upcoming.UpcomingMovie
import com.learetechno.movieapp.data.repository.MovieRepository
import com.learetechno.movieapp.util.ApiException
import com.learetechno.movieapp.util.Constants.Companion.API_KEY
import com.learetechno.movieapp.util.NoInternetException
import com.learetechno.movieapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    val movieRepository: MovieRepository
) : ViewModel() {
    val upcomingMovie : MutableLiveData<Resource<UpcomingMovie>> = MutableLiveData()
    val playNowMovie : MutableLiveData<Resource<UpcomingMovie>> = MutableLiveData()
    val detailsMovie : MutableLiveData<Resource<Details>> = MutableLiveData()
    val searchMovie : MutableLiveData<Resource<UpcomingMovie>> = MutableLiveData()

    init {
        getUpcomingMovie(API_KEY)
        getNowPlayingMovie(API_KEY)

    }

    private fun getUpcomingMovie(apikey : String) = viewModelScope.launch {
        upcomingMovie.postValue(Resource.Loading())
        try {
            val response = movieRepository.getUpcomingMovies(apikey)
            upcomingMovie.postValue(handleUpcomingMovie(response))
        } catch (e : ApiException){
            upcomingMovie.postValue(Resource.Error(e.message.toString()))
        } catch (e : NoInternetException){
            upcomingMovie.postValue(Resource.Error(e.message.toString()))
        }

    }

    private fun getNowPlayingMovie(apikey: String) = viewModelScope.launch {
        playNowMovie.postValue(Resource.Loading())
        try {
            val response = movieRepository.getNowPlayingMovies(apikey)
            playNowMovie.postValue(handlePopularMovie(response))
        } catch (e : ApiException){
            playNowMovie.postValue(Resource.Error(e.message.toString()))
        } catch (e : NoInternetException){
            playNowMovie.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getDetailsMovies(apikey: String, movieId : Int ) = viewModelScope.launch {
        detailsMovie.postValue(Resource.Loading())
        try {
            val response = movieRepository.getDetailsMovies(apikey, movieId)
            detailsMovie.postValue(handleDetailsMovie(response))
        } catch (e : ApiException){
            detailsMovie.postValue(Resource.Error(e.message.toString()))
        } catch (e : NoInternetException){
            detailsMovie.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getSearchMovies(apikey: String, pages : Int, query : String ) = viewModelScope.launch {
        detailsMovie.postValue(Resource.Loading())
        try {
            val response = movieRepository.getSearchMovie(apikey, pages, query)
            searchMovie.postValue(handleSearchMovie(response))
        } catch (e : ApiException){
            searchMovie.postValue(Resource.Error(e.message.toString()))
        } catch (e : NoInternetException){
            searchMovie.postValue(Resource.Error(e.message.toString()))
        }
    }

    private fun handleUpcomingMovie(response: Response<UpcomingMovie>) : Resource<UpcomingMovie>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handlePopularMovie(response: Response<UpcomingMovie>) : Resource<UpcomingMovie>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleDetailsMovie(response: Response<Details>) : Resource<Details>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchMovie(response: Response<UpcomingMovie>) : Resource<UpcomingMovie>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}