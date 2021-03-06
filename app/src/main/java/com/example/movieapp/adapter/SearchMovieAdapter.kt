package com.example.movieapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.common.utils.Constants
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.SearchListItemBinding
import com.example.movieapp.ui.fragment.SearchFragmentDirections

class SearchMovieAdapter : PagingDataAdapter<Movie, SearchMovieAdapter.SearchMovieViewHolder>(DIFF_CALLBACK){

    inner class SearchMovieViewHolder(private val binding : SearchListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(currentMovie: Movie?){
               binding.apply {
                Glide.with(itemView)
                    .load("${Constants.IMAGE_URL}${currentMovie?.poster_path}")
                    .into(searchMovieImageView)
                val date = currentMovie?.release_date ?: "0"
                searchMovieReleaseDate.text = "Yayınlanma Tarihi: $date"
                searchMovieTitleText.text = currentMovie?.title
                searchCircularProgressbar.progress = currentMovie?.vote_average?.toInt() ?: -1
                searchProgressValueText.text = currentMovie?.vote_average?.toString() ?: "0"
            }
        }
    }

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        val currentMovie : Movie? = getItem(position)
        holder.bind(currentMovie)
        holder.itemView.setOnClickListener{
            val action = SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(currentMovie!!)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
        return SearchMovieViewHolder(
            SearchListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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