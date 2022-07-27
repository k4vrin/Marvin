package com.kavrin.marvin.data.paging_source.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kavrin.marvin.data.remote.TMDBMovieService
import com.kavrin.marvin.domain.model.movie.entities.Movie

class SearchMovieSource(
    private val movieService: TMDBMovieService,
    private val query: String
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int {
        return (state.anchorPosition ?: 0) - (state.config.initialLoadSize / 2).coerceAtLeast(0)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val apiResponse = movieService.searchMovies(query = query, page = page)
            val movies = apiResponse.movies
            if (movies.isNotEmpty()) {
                LoadResult.Page(
                    data = movies,
                    prevKey = page - 1,
                    nextKey = page + 1
                )
            } else {
                LoadResult.Page(
                    data = movies,
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}