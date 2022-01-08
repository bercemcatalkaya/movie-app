package com.example.movieapp.common.di

import com.example.movieapp.data.database.MovieDao
import com.example.movieapp.data.network.retrofit.MovieRestInterface
import com.example.movieapp.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        movieRestInterface : MovieRestInterface,
        movieDao: MovieDao
    ) : MovieRepository =  MovieRepository(movieRestInterface = movieRestInterface,
                                            movieDao = movieDao
                            )
}