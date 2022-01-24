package com.example.movieapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.adapter.PopularMovieAdapter
import com.example.movieapp.adapter.TopRatedMovieAdapter
import com.example.movieapp.adapter.UpcomingMovieAdapter
import com.example.movieapp.common.utils.PagingLoadSateAdapter
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),
    PopularMovieAdapter.OnItemClickListener, TopRatedMovieAdapter.OnItemClickListener, UpcomingMovieAdapter.OnItemClickListener
{
    private lateinit var binding : FragmentHomeBinding
    private lateinit var popularMovieAdapter : PopularMovieAdapter
    private lateinit var topRatedMovieAdapter : TopRatedMovieAdapter
    private lateinit var upcomingMovieAdapter : UpcomingMovieAdapter
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
        initTopRatedMovieRecyclerView()
        initUpcomingMovieRecyclerView()
        binding.retryButton.setOnClickListener {
            binding.apply {
                popularMovieTextView.isVisible = true
                topRatedTextView.isVisible = true
                upcomingMoviesTextView.isVisible = true
            }
            popularMovieAdapter.retry()
            topRatedMovieAdapter.retry()
            upcomingMovieAdapter.retry()
        }
        observeMoviesList()
        managePopularMoviesLoadState()
        manageTopRatedMoviesLoadState()
        manageUpcomingMoviesLoadState()
    }

    private fun observeMoviesList(){
        viewModel.popularMoviesList.observe(viewLifecycleOwner,{
            popularMovieAdapter.submitData(viewLifecycleOwner.lifecycle,it)
        })

        viewModel.favoriteMoviesList.observe(viewLifecycleOwner,{
        })

        viewModel.topRatedMoviesList.observe(viewLifecycleOwner,{
            topRatedMovieAdapter.submitData(viewLifecycleOwner.lifecycle,it)
        })

        viewModel.upcomingMoviesList.observe(viewLifecycleOwner,{
            upcomingMovieAdapter.submitData(viewLifecycleOwner.lifecycle,it)
        })
    }

    private fun initPopularMovieRecyclerView() {
        popularMovieAdapter = PopularMovieAdapter(viewModel,this)
        binding.popularMoviesRecyclerView.apply {
            adapter = popularMovieAdapter.withLoadStateHeaderAndFooter(
                header = PagingLoadSateAdapter { popularMovieAdapter.retry() },
                footer = PagingLoadSateAdapter { popularMovieAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

    private fun initTopRatedMovieRecyclerView(){
        topRatedMovieAdapter = TopRatedMovieAdapter(viewModel,this)
        binding.topRatedMoviesRecyclerView.apply {
            adapter = topRatedMovieAdapter.withLoadStateHeaderAndFooter(
                header = PagingLoadSateAdapter { popularMovieAdapter.retry() },
                footer = PagingLoadSateAdapter { popularMovieAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

    private fun initUpcomingMovieRecyclerView(){
        upcomingMovieAdapter = UpcomingMovieAdapter(viewModel,this)
        binding.upcomingMoviesRecyclerView.apply {
            adapter = upcomingMovieAdapter.withLoadStateHeaderAndFooter(
                header = PagingLoadSateAdapter { upcomingMovieAdapter.retry() },
                footer = PagingLoadSateAdapter { upcomingMovieAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

    override fun onItemClick(movie: Movie) {
        val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(movie)
        findNavController().navigate(action)
    }

    private fun managePopularMoviesLoadState(){
        popularMovieAdapter.addLoadStateListener { loadState ->
            binding.apply {
                popularMovieTextView.isVisible = loadState.source.refresh is LoadState.NotLoading
                popularMoviesRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                animationView.isVisible = loadState.source.refresh is LoadState.Error
                retryButton.isVisible = loadState.source.refresh is LoadState.Error


                if(loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && popularMovieAdapter.itemCount < 1 ) {
                    popularMoviesRecyclerView.isVisible = false
                }
            }
        }
    }

    private fun manageTopRatedMoviesLoadState(){
        topRatedMovieAdapter.addLoadStateListener { loadState ->
            binding.apply {
                topRatedTextView.isVisible = loadState.source.refresh is LoadState.NotLoading
                topRatedMoviesRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                if(loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && topRatedMovieAdapter.itemCount < 1 ) {
                    topRatedMoviesRecyclerView.isVisible = false
                }
            }
        }
    }

    private fun manageUpcomingMoviesLoadState(){
        upcomingMovieAdapter.addLoadStateListener { loadState ->
            binding.apply {
                upcomingMoviesTextView.isVisible = loadState.source.refresh is LoadState.NotLoading
                upcomingMoviesRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                if(loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && upcomingMovieAdapter.itemCount < 1 ) {
                    upcomingMoviesRecyclerView.isVisible = false
                }
            }
        }
    }
}