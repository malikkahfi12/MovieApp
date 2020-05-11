package com.example.movieapp.ui.main.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.search.Result
import kotlinx.android.synthetic.main.item_movie.view.*

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.backdropPath == newItem.backdropPath
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    inner class SearchViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener:((Result) -> Unit)? = null
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchMovie = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load("https://image.tmdb.org/t/p/w500${searchMovie.posterPath}").into(img_poster)
            text_title_details.text = searchMovie.title
            text_vote.text = searchMovie.voteAverage.toString()
            setOnClickListener{
                onItemClickListener?.let {
                    it(searchMovie)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: ((Result) -> Unit)){
        onItemClickListener = listener
    }

}