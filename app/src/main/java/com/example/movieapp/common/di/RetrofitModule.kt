package com.example.movieapp.common.di

import com.example.movieapp.common.utils.Constants.BASE_URL
import com.example.movieapp.data.network.retrofit.MovieRestInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideRetrofit() : MovieRestInterface {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MovieRestInterface::class.java)
    }
}