package com.example.movieapp.common.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.NetworkStateItemBinding

class PagingLoadSateAdapter(private val retry : () -> Unit)
    : LoadStateAdapter<PagingLoadSateAdapter.NetworkStateItemViewHolder>() {

    inner class NetworkStateItemViewHolder(
        private val binding : NetworkStateItemBinding
    ) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                errorMessage.isVisible = loadState !is LoadState.Loading
                retryButton.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onBindViewHolder(
        holder: NetworkStateItemViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder =
         NetworkStateItemViewHolder(
            NetworkStateItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
}