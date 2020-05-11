package com.learetechno.movieapp.data.repository

import com.learetechno.movieapp.data.network.ApiInterface
import com.learetechno.movieapp.data.network.SafeApiRequest

class MovieRepository(
    private val api : ApiInterface
) : SafeApiRequest() {

    suspend fun getUpcomingMovies(
        apiKey:String
    )  = api.getListUpcomingMovie(apiKey)

    suspend fun getNowPlayingMovies(
        apiKey: String
    ) = api.getListNowPlaying(apiKey)

    suspend fun getDetailsMovies(
        apiKey: String,
        movieId : Int
    ) = api.getDetailsMovie(movieId, apiKey)

    suspend fun getSearchMovie(
        apiKey: String,
        pages : Int,
        query : String
    ) = api.getSearchMovie(apiKey, pages, query)
}