package com.example.movieapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.adapter.FavoriteMovieAdapter
import com.example.movieapp.common.swipe.SwipeGesture
import com.example.movieapp.databinding.FragmentFavoriteBinding
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteMovieAdapter : FavoriteMovieAdapter
    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFavoriteMovieRecyclerView()
        observeFavoriteMoviesList()
    }

    private fun initFavoriteMovieRecyclerView(){
        favoriteMovieAdapter = FavoriteMovieAdapter()
        binding.favoriteMoviesRecyclerView.apply {
            adapter = favoriteMovieAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }

        val swipeGesture = object : SwipeGesture(){
           override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               viewModel.deleteMovie(favoriteMovieAdapter.differ.currentList[viewHolder.absoluteAdapterPosition])
               favoriteMovieAdapter.notifyItemRemoved(viewHolder.absoluteAdapterPosition)
           }
       }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(binding.favoriteMoviesRecyclerView)
    }

    private fun observeFavoriteMoviesList(){
        viewModel.favoriteMoviesList.observe(viewLifecycleOwner, { favoriteMovies ->
            favoriteMovies?.let {
                favoriteMovieAdapter.differ.submitList(it)
            }
        })
    }
}