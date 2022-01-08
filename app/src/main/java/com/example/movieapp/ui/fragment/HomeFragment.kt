package com.example.movieapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.movieapp.R
import com.example.movieapp.adapter.PopularMovieAdapter
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Constructor
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding

    @Inject
    lateinit var popularMovieAdapter : PopularMovieAdapter
    private val viewModel by viewModels<MovieViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
    }

    private fun initPopularMovieRecyclerView() {
        binding.popularRecyclerView.apply {
            adapter = popularMovieAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }
}