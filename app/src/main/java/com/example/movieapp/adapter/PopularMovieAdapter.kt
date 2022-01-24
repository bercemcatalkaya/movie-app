package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.common.utils.Constants.IMAGE_URL
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.PopularMovieListItemBinding
import com.example.movieapp.ui.fragment.HomeFragmentDirections
import com.example.movieapp.viewmodel.MovieViewModel

class PopularMovieAdapter(
    private val viewModel : MovieViewModel,
    private val listener : OnItemClickListener
) : PagingDataAdapter<Movie,PopularMovieAdapter.PopularMovieViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }
    inner class PopularMovieViewHolder(private val binding: PopularMovieListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(currentMovie : Movie?) {
            binding.apply {
                Glide.with(itemView)
                    .load("$IMAGE_URL${currentMovie?.poster_path}")
                        .into(binding.popularMovieImageView)
                popularMovieTitleText.text = currentMovie?.title
                popularMovieReleaseDateText.text = currentMovie?.release_date
                populateFavorite(currentMovie,binding)
            }
        }
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        val currentMovie : Movie? = getItem(position)
        holder.bind(currentMovie)
        holder.itemView.setOnClickListener{
            listener.onItemClick(currentMovie!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        return PopularMovieViewHolder(
            PopularMovieListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    private fun populateFavorite(currentMovie: Movie?, binding: PopularMovieListItemBinding){
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