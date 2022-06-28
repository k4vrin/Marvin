package com.kavrin.marvin.data.repository

import androidx.paging.PagingData
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending
import com.kavrin.marvin.domain.repository.DataStoreOp
import com.kavrin.marvin.domain.repository.MovieRemoteDataSource
import com.kavrin.marvin.domain.repository.TvRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
	private val dataStore: DataStoreOp,
	private val movieRemote: MovieRemoteDataSource,
	private val tvRemote: TvRemoteDataSource
) {

	///////////////////////////////////////////////////////////////////////////
	// Movie
	///////////////////////////////////////////////////////////////////////////


	fun getAllPopularMovies(): Flow<PagingData<MovieAndPopular>> {
		return movieRemote.getPopularMovies()
	}

	fun getAllTopRatedMovies(): Flow<PagingData<MovieAndTopRated>> {
		return movieRemote.getTopRatedMovies()
	}

	fun getAllTrendingMovies(): Flow<PagingData<MovieAndTrending>> {
		return movieRemote.getTrendingMovies()
	}



	//// Home ////

	fun getCarouselMovies(): Flow<PagingData<MovieAndTrending>> {
		return movieRemote.getCarouselMovies()
	}

	fun getHomePopularMovies(): Flow<PagingData<MovieAndPopular>> {
		return movieRemote.getHomePopularMovies()
	}

	fun getHomeTopRatedMovies(): Flow<PagingData<MovieAndTopRated>> {
		return movieRemote.getHomeTopRatedMovies()
	}

	fun getHomeTrendingMovies(): Flow<PagingData<MovieAndTrending>> {
		return movieRemote.getHomeTrendingMovies()
	}

	///////////////////////////////////////////////////////////////////////////
	// Tv
	///////////////////////////////////////////////////////////////////////////


	fun getAllPopularTvs(): Flow<PagingData<TvAndPopular>> {
		return tvRemote.getPopularTvs()
	}

	fun getAllTopRatedTvs(): Flow<PagingData<TvAndTopRated>> {
		return tvRemote.getTopRatedTvs()
	}

	fun getAllTrendingTvs(): Flow<PagingData<TvAndTrending>> {
		return tvRemote.getTrendingTvs()
	}

	//// Home ////

	fun getCarouselTvs(): Flow<PagingData<TvAndTrending>> {
		return tvRemote.getCarouselTvs()
	}

	fun getHomePopularTvs(): Flow<PagingData<TvAndPopular>> {
		return tvRemote.getHomePopularTvs()
	}

	fun getHomeTopRatedTvs(): Flow<PagingData<TvAndTopRated>> {
		return tvRemote.getHomeTopRatedTvs()
	}

	fun getHomeTrendingTvs(): Flow<PagingData<TvAndTrending>> {
		return tvRemote.getHomeTrendingTvs()
	}

	///////////////////////////////////////////////////////////////////////////
	// DataStore
	///////////////////////////////////////////////////////////////////////////

	suspend fun saveOnBoardingState(completed: Boolean) {
		dataStore.saveOnBoardingState(completed = completed)
	}

	fun readOnBoardingState(): Flow<Boolean> {
		return dataStore.readOnBoardingState()
	}

	suspend fun saveGenres() {
		movieRemote.saveMovieGenres()
		tvRemote.saveTvGenres()
	}
}