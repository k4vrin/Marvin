package com.kavrin.marvin.domain.repository

import androidx.paging.PagingData
import com.kavrin.marvin.domain.model.movie.api.CreditApiResponse
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {

	fun getPopularMovies(): Flow<PagingData<MovieAndPopular>>
	fun getTopRatedMovies(): Flow<PagingData<MovieAndTopRated>>
	fun getTrendingMovies(): Flow<PagingData<MovieAndTrending>>
	fun getCarouselMovies(): Flow<PagingData<MovieAndTrending>>
	fun getHomePopularMovies(): Flow<PagingData<MovieAndPopular>>
	fun getHomeTopRatedMovies(): Flow<PagingData<MovieAndTopRated>>
	fun getHomeTrendingMovies(): Flow<PagingData<MovieAndTrending>>
	fun searchMovies(): Flow<PagingData<Movie>>
	suspend fun saveMovieGenres()
	suspend fun getMovieCredits(id: Int): CreditApiResponse

}