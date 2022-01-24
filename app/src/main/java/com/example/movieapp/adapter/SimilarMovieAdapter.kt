package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.common.utils.Constants
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.SimilarMovieListItemBinding

class SimilarMovieAdapter : RecyclerView.Adapter<SimilarMovieAdapter.SimilarMovieViewHolder>() {
    inner class SimilarMovieViewHolder(private val binding: SimilarMovieListItemBinding)
        : RecyclerView.ViewHolder(binding.root){
            fun bind(movie : Movie?){
                binding.apply {
                    Glide.with(binding.root)
                        .load("${Constants.IMAGE_URL}${movie?.poster_path}")
                        .into(similarMovieImageView)
                    similarMovieTitleText.text = movie?.title
                    similarMovieReleaseDateText.text = movie?.release_date
                }
            }
    }
    override fun onBindViewHolder(holder: SimilarMovieViewHolder, position: Int) {
        val currentMovie : Movie? = differ.currentList[position]
        holder.bind(currentMovie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieViewHolder {
        return SimilarMovieViewHolder(SimilarMovieListItemBinding.inflate(LayoutInflater.from(
            parent.context),parent,false))
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val comparator = object : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }

    val differ = AsyncListDiffer(this,comparator)
}