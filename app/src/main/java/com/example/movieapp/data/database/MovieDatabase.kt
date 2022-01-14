package com.example.movieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.model.Movie

@Database(entities = [Movie::class],version=2)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao() : MovieDao
}