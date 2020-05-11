package com.learetechno.movieapp.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learetechno.movieapp.R
import com.learetechno.movieapp.data.model.banner.SliderBanner
import kotlinx.android.synthetic.main.item_banner.view.*

class BannerSliderAdapter(private val sliderBanner : List<SliderBanner>) : RecyclerView.Adapter<BannerSliderAdapter.BannerViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
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