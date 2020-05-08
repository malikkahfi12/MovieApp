package com.example.movieapp.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import com.example.movieapp.data.model.banner.SliderBanner
import kotlinx.android.synthetic.main.slide_item.view.*

class BannerSliderAdapter(private val sliderBanner : List<SliderBanner>) : RecyclerView.Adapter<BannerSliderAdapter.BannerViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slide_item, parent, false)
        return  BannerViewHolder(view)
    }

    override fun getItemCount(): Int = sliderBanner.size

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(sliderBanner[position])

    }

    inner class BannerViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        private val textTitle = itemView.slide_title
        private val sliderImage = itemView.slide_img

        fun bind(sliderBanner : SliderBanner){
            textTitle.text = sliderBanner.title
            sliderImage.setImageResource(sliderBanner.image)
        }
    }
}