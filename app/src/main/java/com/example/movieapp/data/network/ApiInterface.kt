package com.example.movieapp.data.network

import com.example.movieapp.data.model.details.Details
import com.example.movieapp.data.model.upcoming.UpcomingMovie
import com.example.movieapp.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiInterface {
    @GET("3/movie/upcoming")
    suspend fun getListUpcomingMovie(
        @Query(value = "api_key") apiKey:String,
        @Query(value = "language") languange:String = "en-US",
        @Query(value = "page") pages:Int = 1
    ) : Response<UpcomingMovie>

    @GET("3/movie/popular")
    suspend fun getListNowPlaying(
        @Query(value = "api_key") apiKey: String,
        @Query(value = "language") languange: String = "en-US",
        @Query(value = "page") pages: Int = 1
    ) : Response<UpcomingMovie>

    @GET("3/movie/{movie_id}")
    suspend fun getDetailsMovie(
        @Path("movie_id") movieId : Int,
        @Query("api_key") apiKey: String,
        @Query("language") languange: String = "en-US",
        @Query("append_to_response") appendToResponse : String = "videos"
    ) : Response<Details>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : ApiInterface{
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }
}