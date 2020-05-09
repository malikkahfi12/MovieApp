package com.example.movieapp.data.repository

import com.example.movieapp.data.network.ApiInterface
import com.example.movieapp.data.network.SafeApiRequest

class UpcomingRepository(
    private val api : ApiInterface
) : SafeApiRequest() {

    suspend fun getUpcomingMovies(
        apiKey:String
    )  = api.getListUpcomingMovie(apiKey)

    suspend fun getNowPlayingMovies(
        apiKey: String
    ) = api.getListNowPlaying(apiKey)
}