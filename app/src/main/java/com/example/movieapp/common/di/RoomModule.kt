package com.example.movieapp.common.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.database.MovieDao
import com.example.movieapp.data.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context : Context) : MovieDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "movie-database"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideMovieDao(movieDatabase: MovieDatabase) : MovieDao =
        movieDatabase.getMovieDao()
}