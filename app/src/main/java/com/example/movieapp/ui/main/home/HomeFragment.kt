package com.example.movieapp.ui.main.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import com.example.movieapp.data.model.banner.SliderBanner
import com.example.movieapp.ui.main.MainActivity
import com.example.movieapp.ui.main.MainViewModel
import com.example.movieapp.util.Resource
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var viewModel: MainViewModel
    lateinit var upComingAdapter : UpcomingAdapter
    lateinit var popularAdapter: PopularAdapter

    val sliderHandler = Handler()
    val TAG = "HomeFragment"
    val sliderRunnable = Runnable {
        var position = slide_pager.currentItem
        position +=1
        if (position >= sliderBannerAdapter.itemCount){
            position = 0
        }
        slide_pager.setCurrentItem(position)
    }
    private val sliderBannerAdapter = BannerSliderAdapter(
        listOf(
            SliderBanner(
                R.drawable.banner_1,
            "The Call of the Wild"
            ),
            SliderBanner(
                R.drawable.banner_2,
                "The Platform"
            ),
            SliderBanner(
                R.drawable.banner_3,
                "The Wretched"
            ),
            SliderBanner(
                R.drawable.banner_4,
                "The Last Full Measure"
            )
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        slide_pager.adapter = sliderBannerAdapter

        slide_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000)
            }
        })
        setupRecyclerView()

        // onClick
        upComingAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("upcoming", it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
                bundle
            )
        }

        popularAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("upcoming", it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
                bundle
            )
        }



        viewModel.upcomingMovie.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let { newsResponse ->
                        upComingAdapter.differ.submitList(newsResponse.results)
                    }
                }
                is Resource.Error -> {
//                    hideProgressBar()
                    errorNetwork()
                    it.message?.let {
                        Log.e(TAG, "An Error occured : $it")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        viewModel.playNowMovie.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let { newsResponse ->
                        popularAdapter.differ.submitList(newsResponse.results)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    it.message?.let {
                        Log.e(TAG, "An Error occured : $it")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

    }

    private fun setupRecyclerView() {
        upComingAdapter = UpcomingAdapter()
        popularAdapter = PopularAdapter()
        rvUpcomingMovie.apply {
            adapter = upComingAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
        rvNowPlaying.apply {
            adapter = popularAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }


    private fun errorNetwork(){
        group_connections.visibility = View.VISIBLE
        progress_bar.visibility = View.INVISIBLE
        group_items.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        progress_bar.visibility = View.INVISIBLE
        group_items.visibility = View.VISIBLE
    }

    private fun showProgressBar(){
        progress_bar.visibility = View.VISIBLE
        group_items.visibility = View.INVISIBLE
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 3000)
    }

}