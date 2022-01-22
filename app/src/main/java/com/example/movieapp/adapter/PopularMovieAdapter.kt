package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.common.utils.Constants.IMAGE_URL
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.PopularListItemBinding
import com.example.movieapp.viewmodel.MovieViewModel

class PopularMovieAdapter(
    private val viewModel : MovieViewModel
) : PagingDataAdapter<Movie,PopularMovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(movie: Movie?, status : Boolean)
    }
    inner class MovieViewHolder(private val binding: PopularListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(currentMovie : Movie?) {
            binding.apply {
                Glide.with(itemView)
                    .load("$IMAGE_URL${currentMovie?.poster_path}")
                        .into(binding.popularMovieImageView)
                populateFavorite(currentMovie,binding)
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

    private fun populateFavorite(currentMovie: Movie?, binding: PopularListItemBinding){
        if( viewModel.favoriteMoviesList.value?.find {
                it.id == currentMovie?.id } == null)
        {
            currentMovie?.favoriteStatus = false
            binding.favoriteImageView.setImageResource(R.drawable.ic_favorite)
        }
        else
        {
            currentMovie?.favoriteStatus = true
            binding.favoriteImageView.setImageResource(R.drawable.ic_favorite_filled)
        }

        binding.favoriteImageView.setOnClickListener{
            if( currentMovie?.favoriteStatus == true ) {
                currentMovie.favoriteStatus = false
                viewModel.deleteMovie(currentMovie)
                binding.favoriteImageView.setImageResource(R.drawable.ic_favorite)
            }
            else{
                currentMovie?.favoriteStatus = true
                if (currentMovie != null) {
                    viewModel.insertMovie(currentMovie)
                }
                binding.favoriteImageView.setImageResource(R.drawable.ic_favorite_filled)
            }
        }
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