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

class UpcomingAdapter : RecyclerView.Adapter<UpcomingAdapter.UpcomingViewHolder>(){
    inner class UpcomingViewHolder(view : View) : RecyclerView.ViewHolder(view)

    private val differCallback = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.backdropPath == newItem.backdropPath
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        return UpcomingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size


    private var onItemClickListener:((Result) -> Unit)? = null
    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val upcomingMovie = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load("https://image.tmdb.org/t/p/w500${upcomingMovie.posterPath}").into(img_poster)
            text_title_details.text = upcomingMovie.title
            text_vote.text = upcomingMovie.voteAverage.toString()
            setOnClickListener{
                onItemClickListener?.let {
                    it(upcomingMovie)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (Result) -> Unit){
        onItemClickListener = listener
    }

}