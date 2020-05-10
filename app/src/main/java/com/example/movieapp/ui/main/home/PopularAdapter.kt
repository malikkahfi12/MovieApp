package com.example.movieapp.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.upcoming.Result
import kotlinx.android.synthetic.main.item_movie.view.*

class PopularAdapter : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.backdropPath == newItem.backdropPath
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }
    inner class PopularViewHolder(view : View) : RecyclerView.ViewHolder(view)

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size
    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val upcomingMovie = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load("https://image.tmdb.org/t/p/w500${upcomingMovie.posterPath}").into(img_poster)
            text_title.text = upcomingMovie.title
            text_vote.text = upcomingMovie.voteAverage.toString()
        }
    }
}