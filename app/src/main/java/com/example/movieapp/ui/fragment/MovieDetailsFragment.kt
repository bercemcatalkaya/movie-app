package com.example.movieapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.adapter.MovieCastAdapter
import com.example.movieapp.adapter.SimilarMovieAdapter
import com.example.movieapp.adapter.UpcomingMovieAdapter
import com.example.movieapp.common.utils.Constants
import com.example.movieapp.common.utils.Constants.TAG
import com.example.movieapp.common.utils.Status
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private lateinit var binding : FragmentMovieDetailsBinding
    private lateinit var castAdapter: MovieCastAdapter
    private lateinit var similarMovieAdapter: SimilarMovieAdapter
    private lateinit var currentMovie : Movie
    private val args by navArgs<MovieDetailsFragmentArgs>()
    private val viewModel by viewModels<MovieViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentMovie = args.movie

        populateMovieDetail(currentMovie)
        initCastRecyclerView()
        initSimilarMovieRecyclerView()
        viewModel.getMovieCredits(movieId = currentMovie.id)
        observeCastList()

        viewModel.getSimilarMovies(currentMovie.id)
        observeSimilarMoviesList()
    }

    @SuppressLint("SetTextI18n")
    private fun populateMovieDetail(currentMovie: Movie){
        binding.apply {
            Glide.with(binding.root)
                .load("${Constants.IMAGE_URL}${currentMovie.poster_path}")
                .into(movieImageView)
            movieNameText.text = currentMovie.title
            movieReleaseDate.text = currentMovie.release_date
            circularProgressbar.progress = (currentMovie.vote_average*10).toInt()
            progressValueText.text = "% "+ (currentMovie.vote_average*10).toString()
            summaryExpandableText.text = currentMovie.overview
        }
    }
    private fun initCastRecyclerView(){
        castAdapter = MovieCastAdapter()
        binding.movieCastRecyclerView.apply {
            adapter = castAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

    private fun observeCastList(){
        viewModel.movieCastList.observe(viewLifecycleOwner, { resource ->
            when(resource.status){
                Status.SUCCESS -> castAdapter.differ.submitList(resource.data)
                Status.LOADING -> Log.d(TAG,"Loading")
                else -> Log.d(TAG,"Error")
            }
        })
    }

    private fun initSimilarMovieRecyclerView(){
        similarMovieAdapter = SimilarMovieAdapter()
        binding.similarMovieRecyclerView.apply {
            adapter = similarMovieAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }


    private fun observeSimilarMoviesList(){
        viewModel.similarMoviesList.observe(viewLifecycleOwner, { resource ->
            when(resource.status){
                Status.SUCCESS -> similarMovieAdapter.differ.submitList(resource.data)
                Status.LOADING -> Log.d(TAG,"Loading")
                else -> Log.d(TAG,"Error")
            }
        })
    }
}