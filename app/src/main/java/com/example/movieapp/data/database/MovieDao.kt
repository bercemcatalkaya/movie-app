package com.example.movieapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movieapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Query("SELECT * FROM movies")
    fun getPopularMovies() : Flow<List<Movie>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId : Int) : LiveData<Movie>
}