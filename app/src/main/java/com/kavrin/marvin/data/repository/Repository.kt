package com.kavrin.marvin.data.repository

import androidx.paging.PagingData
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending
import com.kavrin.marvin.domain.repository.DataStoreOp
import com.kavrin.marvin.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
	private val dataStore: DataStoreOp,
	private val remote: RemoteDataSource
) {

	fun getAllPopularMovies(): Flow<PagingData<MovieAndPopular>> {
		return remote.getPopularMovies()
	}

	fun getAllTopRatedMovies(): Flow<PagingData<MovieAndTopRated>> {
		return remote.getTopRatedMovies()
	}

	fun getAllTrendingMovies(): Flow<PagingData<MovieAndTrending>> {
		return remote.getTrendingMovies()
	}

	fun getMovieCarousel(): Flow<PagingData<MovieAndTrending>> {
		return remote.getCarouselMovies()
	}

	fun getAllPopularTvs(): Flow<PagingData<TvAndPopular>> {
		return remote.getPopularTvs()
	}

	fun getAllTopRatedTvs(): Flow<PagingData<TvAndTopRated>> {
		return remote.getTopRatedTvs()
	}

	fun getAllTrendingTvs(): Flow<PagingData<TvAndTrending>> {
		return remote.getTrendingTvs()
	}

	suspend fun saveOnBoardingState(completed: Boolean) {
		dataStore.saveOnBoardingState(completed = completed)
	}

	fun readOnBoardingState(): Flow<Boolean> {
		return dataStore.readOnBoardingState()
	}
}