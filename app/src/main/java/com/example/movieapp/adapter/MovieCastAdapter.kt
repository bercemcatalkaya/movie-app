package com.example.movieapp.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.common.utils.Constants.IMAGE_URL
import com.example.movieapp.data.model.cast.Cast
import com.example.movieapp.databinding.CastListItemBinding

class MovieCastAdapter : RecyclerView.Adapter<MovieCastAdapter.CastViewHolder>() {
    inner class CastViewHolder(private val binding : CastListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bind(currentCast : Cast?){
                binding.apply {
                    Glide.with(itemView)
                        .load("$IMAGE_URL${currentCast?.profile_path}")
                        .into(castImageView)
                    castNameText.text = currentCast?.name
                    originalCastNameText.text = currentCast?.original_name
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(CastListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val currentCast : Cast? = differ.currentList[position]
        currentCast?.let {
            holder.bind(currentCast)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val comparator = object : DiffUtil.ItemCallback<Cast>(){
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.cast_id == newItem.cast_id
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,comparator)
}