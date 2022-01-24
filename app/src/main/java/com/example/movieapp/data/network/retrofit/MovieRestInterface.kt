package com.example.movieapp.data.network.retrofit

import com.example.movieapp.data.model.MovieResponse
import com.example.movieapp.data.model.cast.CastResponse
import com.example.movieapp.data.model.similarmovie.SimilarMovieResponse
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


    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") query : Int,
        @Query("api_key") apiKey : String
    ) : CastResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") query : Int,
        @Query("api_key") apiKey: String,
        @Query("page") page : Int
    ) : MovieResponse
}