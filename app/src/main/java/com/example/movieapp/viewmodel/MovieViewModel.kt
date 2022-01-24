package com.example.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.common.utils.Constants.API_KEY
import com.example.movieapp.common.utils.Constants.POPULAR_URL
import com.example.movieapp.common.utils.Constants.TAG
import com.example.movieapp.common.utils.Constants.TOP_RATED_URL
import com.example.movieapp.common.utils.Constants.UPCOMING_URL
import com.example.movieapp.common.utils.Resource
import com.example.movieapp.common.utils.Status
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.cast.Cast
import com.example.movieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel(){

    private lateinit var popularMovies : LiveData<PagingData<Movie>>
    val popularMoviesList : LiveData<PagingData<Movie>>
        get() = popularMovies

    private var favoriteMovies : LiveData<List<Movie>> = movieRepository.favoriteMoviesList.asLiveData()
    val favoriteMoviesList : LiveData<List<Movie>>
        get()  = favoriteMovies

    private lateinit var searchMovies : LiveData<PagingData<Movie>>
    val searchMoviesList : LiveData<PagingData<Movie>>
        get() = searchMovies

    private lateinit var topRatedMovies : LiveData<PagingData<Movie>>
    val topRatedMoviesList : LiveData<PagingData<Movie>>
        get() = topRatedMovies

    private lateinit var upcomingMovies : LiveData<PagingData<Movie>>
    val upcomingMoviesList : LiveData<PagingData<Movie>>
        get() = upcomingMovies

    private var movieCasts : MutableLiveData<Resource<List<Cast>>> = MutableLiveData<Resource<List<Cast>>>()
    val movieCastList : LiveData<Resource<List<Cast>>>
        get() = movieCasts

    private var similarMovies : MutableLiveData<Resource<List<Movie>>> = MutableLiveData<Resource<List<Movie>>>()
    val similarMoviesList : LiveData<Resource<List<Movie>>>
        get() = similarMovies


    init{
        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
    }

    private fun getPopularMovies() {
        popularMovies = getMoviesOf(POPULAR_URL)
    }

    private fun getTopRatedMovies() {
        topRatedMovies = getMoviesOf(TOP_RATED_URL)
    }

    private fun getUpcomingMovies() {
        upcomingMovies = getMoviesOf(UPCOMING_URL)
    }

    fun searchMovies(query : String) {
        try{
            searchMovies = movieRepository.searchMovies(query).asLiveData().cachedIn(viewModelScope)
        }catch ( e: Exception){
            Log.d(TAG,"Error occurred:${e.printStackTrace()}")
        }
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

     fun insertMovie(selectedMovie : Movie){
        viewModelScope.launch {
            try {
                movieRepository.insertMovie(selectedMovie)
            } catch (e: Exception) {
                Log.d(TAG, "Error occurred ${e.printStackTrace()}")
            }
        }
    }

    fun deleteMovie(selectedMovie: Movie){
        viewModelScope.launch {
            try {
                movieRepository.deleteMovie(movie = selectedMovie)
            } catch (e: java.lang.Exception){
                Log.d(TAG,"Error occurred ${e.printStackTrace()}")
            }
        }
    }

    fun getMovieCredits(movieId : Int, apiKey : String = API_KEY){
        viewModelScope.launch {
            movieCasts.postValue(Resource.loading(null))
            try{
                val response = movieRepository.getMovieCredits(movieId,apiKey)
                movieCasts.postValue(Resource.success(response.cast))
            }catch (e: java.lang.Exception){
                Log.d(TAG,"Error occurred ${e.printStackTrace()}")
                movieCasts.postValue(Resource.error(e.printStackTrace().toString(),null))
            }
        }
    }

    fun getSimilarMovies(movieId : Int, apiKey : String = API_KEY){
        viewModelScope.launch {
            similarMovies.postValue(Resource.loading(null))
            try{
                val response = movieRepository.getSimilarMovies(movieId,apiKey)
                similarMovies.postValue(Resource.success(response.results))
            }catch (e: java.lang.Exception){
                Log.d(TAG,"Error occurred ${e.printStackTrace()}")
                similarMovies.postValue(Resource.error(e.printStackTrace().toString(),null))
            }
        }
    }
}
