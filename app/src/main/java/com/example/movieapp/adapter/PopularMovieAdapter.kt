package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.movieapp.common.utils.Constants.IMAGE_URL
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.PopularListItemBinding
import javax.inject.Inject

class PopularMovieAdapter @Inject constructor(
  val glide : RequestManager
) : PagingDataAdapter<Movie,PopularMovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    inner class MovieViewHolder(private val binding: PopularListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(currentMovie : Movie?) {
            binding.apply {
                    glide.load("$IMAGE_URL${currentMovie?.poster_path}")
                        .into(binding.popularMovieImageView)
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie : Movie? = getItem(position)
        holder.bind(currentMovie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            PopularListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}