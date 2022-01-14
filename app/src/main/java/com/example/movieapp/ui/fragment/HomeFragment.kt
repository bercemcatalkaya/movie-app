package com.example.movieapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.adapter.PopularMovieAdapter
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), PopularMovieAdapter.OnItemClickListener{
    private lateinit var binding : FragmentHomeBinding
    private lateinit var popularMovieAdapter : PopularMovieAdapter
    private val viewModel by viewModels<MovieViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPopularMovieRecyclerView()
        viewModel.popularMoviesList.observe(viewLifecycleOwner,{
            popularMovieAdapter.submitData(viewLifecycleOwner.lifecycle,it)
        })

        viewModel.favoriteMoviesList.observe(viewLifecycleOwner,{

        })
    }

    private fun initPopularMovieRecyclerView() {
        popularMovieAdapter = PopularMovieAdapter(viewModel)
        binding.popularRecyclerView.apply {
            adapter = popularMovieAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

    override fun onItemClick(movie: Movie?, status : Boolean) {
        if( status )
            viewModel.insertMovie(movie!!)
        else
            viewModel.deleteMovie(movie!!)
    }
}