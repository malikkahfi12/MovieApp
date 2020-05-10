package com.example.movieapp.ui.intro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.model.intro.IntroSlider
import kotlinx.android.synthetic.main.item_slide_container.view.*

class IntroSliderAdapter(private val introSlides: List<IntroSlider>) : RecyclerView.Adapter<IntroSliderAdapter.IntroSliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slide_container, parent, false)
        return IntroSliderViewHolder(view)
    }

    override fun getItemCount(): Int = introSlides.size

    override fun onBindViewHolder(holder: IntroSliderViewHolder, position: Int) {
        holder.bind(introSlides[position])
    }

    inner class IntroSliderViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val textTitle = view.text_title_details
        private val textDescription = view.text_description_details
        private val imageIcon = view.image_icon

        fun bind(introSlider: IntroSlider){
            textTitle.text = introSlider.title
            textDescription.text = introSlider.description
            imageIcon.setImageResource(introSlider.image)
        }
    }




}