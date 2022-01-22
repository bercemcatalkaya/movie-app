package com.example.movieapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.adapter.SearchMovieAdapter
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding
    private lateinit var searchResultAdapter: SearchMovieAdapter
    private val viewModel by viewModels<MovieViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchResultRecyclerView()
        manageSearchMoviesLoadState()

        binding.movieSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                if(query != null && query != "" )
                {
                    val ps = Pattern.compile("^[A-Za-z0-9_-]*$")
                    val ms = ps.matcher(query)
                    if( ms.matches() ){
                        searchMovies(query)
                    }
                }
                else
                    searchResultAdapter.submitData(lifecycle = viewLifecycleOwner.lifecycle, PagingData.empty())
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
               return false
            }
        })
    }

    private fun searchMovies(query: String) {
        viewModel.searchMovies(query)

        viewModel.searchMoviesList.observe(viewLifecycleOwner, {
            searchResultAdapter.submitData(lifecycle = viewLifecycleOwner.lifecycle, it)
        })
    }

    private fun initSearchResultRecyclerView(){
        searchResultAdapter = SearchMovieAdapter()
        binding.searchResultRecyclerView.apply {
            adapter = searchResultAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }
    }
    private fun manageSearchMoviesLoadState(){
        binding.retryButton.setOnClickListener{
            searchResultAdapter.retry()
        }
        searchResultAdapter.addLoadStateListener { loadState ->
            binding.apply {
                searchResultRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                animationView.isVisible = loadState.source.refresh is LoadState.Error
                retryButton.isVisible = loadState.source.refresh is LoadState.Error
                movieSearchView.isVisible = (loadState.source.refresh is LoadState.Loading) || (loadState.source.refresh is LoadState.NotLoading)


                if(loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && searchResultAdapter.itemCount < 1 ) {
                    searchResultRecyclerView.isVisible = false
                }
            }
        }
    }
}