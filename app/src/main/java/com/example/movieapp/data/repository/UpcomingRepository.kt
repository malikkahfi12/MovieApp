package com.example.movieapp.data.repository

import com.example.movieapp.data.network.RetrofitInstance
import com.example.movieapp.data.network.SafeApiRequest

class UpcomingRepository() : SafeApiRequest() {

    suspend fun getUpcomingMovies(
        apiKey:String
    )  = RetrofitInstance.api.getListUpcomingMovie(apiKey)

}