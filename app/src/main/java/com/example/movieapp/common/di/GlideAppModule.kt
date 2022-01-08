package com.example.movieapp.common.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlideAppModule {

    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context : Context) = Glide.with(context)
            .setDefaultRequestOptions(
                RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground))

}