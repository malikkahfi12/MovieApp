package com.example.movieapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.movieapp.R
import com.example.movieapp.data.network.ApiInterface
import com.example.movieapp.data.network.NetworkConnectionInterceptor
import com.example.movieapp.data.repository.MovieRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = ApiInterface(networkConnectionInterceptor)
        val upcomingRepository = MovieRepository(api)
        val movieViewModelProviderFactory = MovieViewModelProviderFactory(upcomingRepository)

        viewModel = ViewModelProvider(this, movieViewModelProviderFactory).get(MainViewModel::class.java)

        navController = Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
        bottom_nav.setupWithNavController(navController)


    }
}
