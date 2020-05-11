package com.example.movieapp.data.repository

import com.example.movieapp.data.network.ApiInterface
import com.example.movieapp.data.network.SafeApiRequest

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