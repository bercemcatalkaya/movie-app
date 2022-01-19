package com.example.movieapp.data.network.retrofit

import com.example.movieapp.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieRestInterface {
    @GET("movie/{query}")
    suspend fun getMovies(
        @Path("query") query : String,
        @Query("api_key") apiKey : String,
        @Query("page") page : Int,
    ) : MovieResponse

    @GET("search/movie/")
    suspend fun getSearchMovie(
        @Query("query") query : String,
        @Query("api_key") apiKey : String,
        @Query("page") page : Int
    ) : MovieResponse
}