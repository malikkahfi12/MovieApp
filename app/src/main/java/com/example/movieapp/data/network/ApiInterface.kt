package com.example.movieapp.data.network

import com.example.movieapp.data.model.upcoming.UpcomingMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("3/movie/upcoming")
    suspend fun getListUpcomingMovie(
        @Query(value = "api_key") apiKey:String,
        @Query(value = "language") languange:String = "en-US",
        @Query(value = "page") pages:Int = 1
    ) : Response<UpcomingMovie>

//    companion object{
//        operator fun invoke(
//            networkConnectionInterceptor: NetworkConnectionInterceptor
//        ) : ApiInterface{
//            val okHttpClient = OkHttpClient.Builder()
//                .addInterceptor(networkConnectionInterceptor)
//                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .connectTimeout(1, TimeUnit.MINUTES)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build()
//
//            return Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(ApiInterface::class.java)
//        }
//    }
}