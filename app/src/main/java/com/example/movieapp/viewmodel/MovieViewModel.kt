package com.example.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.common.utils.Constants.POPULAR_URL
import com.example.movieapp.common.utils.Constants.TAG
import com.example.movieapp.data.model.Movie
import com.example.movieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel(){

    private lateinit var popularMovies : LiveData<PagingData<Movie>>
    val popularMoviesList : LiveData<PagingData<Movie>>
        get() = popularMovies

    init{
        getPopularMovies()
    }

    private fun getPopularMovies() {
        popularMovies = getMoviesOf(POPULAR_URL)
    }
    private fun getMoviesOf(type: String) : LiveData<PagingData<Movie>>  {
        var data: LiveData<PagingData<Movie>>? = null
        try{
            data = movieRepository.getMovies(type).asLiveData().cachedIn(viewModelScope)
        }catch ( e: Exception){
            Log.d(TAG,"Error occurred:${e.printStackTrace()}")
        }
        return data!!
    }
}
