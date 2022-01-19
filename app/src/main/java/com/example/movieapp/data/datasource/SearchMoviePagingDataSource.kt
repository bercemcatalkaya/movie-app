package com.example.movieapp.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.common.utils.Constants
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieResponse
import com.example.movieapp.data.network.retrofit.MovieRestInterface
import retrofit2.HttpException
import java.io.IOException

class SearchMoviePagingDataSource(
    private val movieRestInterface: MovieRestInterface,
    private val query : String
) : PagingSource<Int, Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: Constants.DEFAULT_PAGE_INDEX
        return try{
            val response : MovieResponse =
                movieRestInterface.getSearchMovie(
                    query = query,
                    apiKey = Constants.API_KEY,
                    page = position
                )
            LoadResult.Page(
                data = response.results,
                prevKey = if (position == Constants.DEFAULT_PAGE_INDEX) null else position - 1,
                nextKey = if (response.results.isEmpty()) null else position + 1
            )
        }catch ( e : IOException){
            Log.d(Constants.TAG, "Error occurred:${e.printStackTrace()}")
            LoadResult.Error(e)
        }catch (e : HttpException){
            Log.d(Constants.TAG, "Error occurred:${e.printStackTrace()}")
            LoadResult.Error(e)
        }
    }
}