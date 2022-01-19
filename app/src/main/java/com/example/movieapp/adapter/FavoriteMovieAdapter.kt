package com.example.movieapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.common.utils.Constants
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.FavoriteListItemBinding
import java.text.SimpleDateFormat
import java.util.*

class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>() {

    inner class FavoriteMovieViewHolder(private val binding : FavoriteListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(currentMovie: Movie?) {
            binding.apply {
                Glide.with(itemView)
                    .load("${Constants.IMAGE_URL}${currentMovie?.poster_path}")
                    .into(favoriteMovieImageView)

                val date = getDateFormat(currentMovie?.release_date ?: "0")
                favoriteMovieReleaseDate.text = "Yayınlanma Tarihi: $date"
                favoriteMovieTitleText.text = currentMovie?.title
                circularProgressbar.progress = currentMovie?.vote_average?.toInt() ?: -1
                progressValueText.text = currentMovie?.vote_average?.toString() ?: "0"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        return FavoriteMovieViewHolder(FavoriteListItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        val currentFavoriteMovie : Movie? = differ.currentList[position]
        currentFavoriteMovie?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val comparator = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.id == newItem.id
    }
    var differ = AsyncListDiffer(this, comparator)

    @SuppressLint("SimpleDateFormat")
    private fun getDateFormat(date : String) : String {
        val initDate: Date? = SimpleDateFormat("yyyy-MM-dd").parse(date)
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        return formatter.format(initDate!!)
    }
}