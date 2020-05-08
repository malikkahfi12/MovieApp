package com.example.movieapp.data.model.upcoming


import com.example.movieapp.data.model.upcoming.Dates
import com.example.movieapp.data.model.upcoming.Result
import com.google.gson.annotations.SerializedName

data class UpcomingMovie(
    @SerializedName("dates")
    val dates: Dates,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)