package com.example.movieapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.database.MovieDao
import com.example.movieapp.data.datasource.MoviePagingDataSource
import com.example.movieapp.data.datasource.SearchMoviePagingDataSource
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.network.retrofit.MovieRestInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieRestInterface: MovieRestInterface,
    private val movieDao: MovieDao
){

    val favoriteMoviesList = movieDao.getFavoriteMovies()
    suspend fun insertMovie(movie: Movie) =
        movieDao.insertMovie(movie)

    suspend fun deleteMovie(movie: Movie) =
        movieDao.deleteMovie(movie)

    fun getMovies(query: String) : Flow<PagingData<Movie>>{
            return Pager(
                config = PagingConfig(
                    pageSize = 20,
                    maxSize = 100,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    MoviePagingDataSource(movieRestInterface, query) }
            ).flow
    }

    fun searchMovies(query : String) : Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchMoviePagingDataSource(movieRestInterface,query)
            }).flow
    }

    suspend fun getMovieCredits(movieId : Int, apiKey : String) =
        movieRestInterface.getMovieCredits(movieId,apiKey)

    suspend fun getSimilarMovies(movieId : Int, apiKey: String) =
        movieRestInterface.getSimilarMovies(movieId,apiKey,1)
}