package com.kavrin.marvin.domain.repository

import androidx.paging.PagingData
import com.kavrin.marvin.domain.model.movie.api.collection.MovieCollection
import com.kavrin.marvin.domain.model.movie.api.detail.SingleMovieApiResponse
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieRemoteDataSource {

	fun getPopularMovies(): Flow<PagingData<MovieAndPopular>>
	fun getTopRatedMovies(): Flow<PagingData<MovieAndTopRated>>
	fun getTrendingMovies(): Flow<PagingData<MovieAndTrending>>
	fun getCarouselMovies(): Flow<PagingData<MovieAndTrending>>
	fun getHomePopularMovies(): Flow<PagingData<MovieAndPopular>>
	fun getHomeTopRatedMovies(): Flow<PagingData<MovieAndTopRated>>
	fun getHomeTrendingMovies(): Flow<PagingData<MovieAndTrending>>
	fun searchMovies(query: String): Flow<PagingData<Movie>>
	suspend fun saveMovieGenres()
	suspend fun getMovieDetails(id: Int): Response<SingleMovieApiResponse>
	suspend fun getMovieCollection(id: Int): Response<MovieCollection>

}