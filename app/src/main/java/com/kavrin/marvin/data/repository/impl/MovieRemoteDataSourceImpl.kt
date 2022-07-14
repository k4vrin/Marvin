package com.kavrin.marvin.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.data.paging_source.movie.MoviePopularRemoteMediator
import com.kavrin.marvin.data.paging_source.movie.MovieTopRatedRemoteMediator
import com.kavrin.marvin.data.paging_source.movie.MovieTrendingRemoteMediator
import com.kavrin.marvin.data.remote.TMDBMovieService
import com.kavrin.marvin.domain.model.movie.api.collection.MovieCollection
import com.kavrin.marvin.domain.model.movie.api.detail.SingleMovieApiResponse
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import com.kavrin.marvin.domain.repository.MovieRemoteDataSource
import com.kavrin.marvin.util.Constants.DEFAULT_HOME_PREFETCH
import com.kavrin.marvin.util.Constants.ITEMS_IN_HOME
import com.kavrin.marvin.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class MovieRemoteDataSourceImpl(
	private val movieService: TMDBMovieService,
	private val marvinDatabase: MarvinDatabase,
) : MovieRemoteDataSource {

	///////////////////////////////////////////////////////////////////////////
	// All
	///////////////////////////////////////////////////////////////////////////
	override fun getPopularMovies(): Flow<PagingData<MovieAndPopular>> {
		val pagingSourceFactory = {
			marvinDatabase.moviePopularDao().getMovieAndPopular()
		}

		return Pager(
			config = PagingConfig(pageSize = ITEMS_PER_PAGE),
			remoteMediator = MoviePopularRemoteMediator(
				movieService = movieService,
				marvinDatabase = marvinDatabase
			),
			pagingSourceFactory = pagingSourceFactory
		).flow
	}

	override fun getTopRatedMovies(): Flow<PagingData<MovieAndTopRated>> {
		val pagingSourceFactory = {
			marvinDatabase.movieTopRatedDao().getMovieAndTopRated()
		}

		return Pager(
			config = PagingConfig(pageSize = ITEMS_PER_PAGE),
			remoteMediator = MovieTopRatedRemoteMediator(
				movieService = movieService,
				marvinDatabase = marvinDatabase
			),
			pagingSourceFactory = pagingSourceFactory
		).flow
	}

	override fun getTrendingMovies(): Flow<PagingData<MovieAndTrending>> {
		val pagingSourceFactory = {
			marvinDatabase.movieTrendingDao().getMovieAndTrending()
		}

		return Pager(
			config = PagingConfig(pageSize = ITEMS_PER_PAGE),
			remoteMediator = MovieTrendingRemoteMediator(
				movieService = movieService,
				marvinDatabase = marvinDatabase
			),
			pagingSourceFactory = pagingSourceFactory
		).flow
	}

	///////////////////////////////////////////////////////////////////////////
	// Home
	///////////////////////////////////////////////////////////////////////////
	override fun getCarouselMovies(): Flow<PagingData<MovieAndTrending>> {
		val pagingSourceFactory = {
			marvinDatabase.movieTrendingDao().getCarouselMovies()
		}

		return Pager(
			config = PagingConfig(
				pageSize = ITEMS_IN_HOME,
				maxSize = ITEMS_IN_HOME * 3,
				prefetchDistance = DEFAULT_HOME_PREFETCH,
			),
			remoteMediator = MovieTrendingRemoteMediator(
				movieService = movieService,
				marvinDatabase = marvinDatabase
			),
			pagingSourceFactory = pagingSourceFactory
		).flow
	}

	override fun getHomePopularMovies(): Flow<PagingData<MovieAndPopular>> {
		val pagingSourceFactory = {
			marvinDatabase.moviePopularDao().getHomeMovieAndPopular()
		}

		return Pager(
			config = PagingConfig(
				pageSize = ITEMS_IN_HOME,
				maxSize = ITEMS_IN_HOME * 3,
				prefetchDistance = DEFAULT_HOME_PREFETCH,
			),
			remoteMediator = MoviePopularRemoteMediator(
				movieService = movieService,
				marvinDatabase = marvinDatabase
			),
			pagingSourceFactory = pagingSourceFactory
		).flow	}

	override fun getHomeTopRatedMovies(): Flow<PagingData<MovieAndTopRated>> {
		val pagingSourceFactory = {
			marvinDatabase.movieTopRatedDao().getHomeMovieAndTopRated()
		}

		return Pager(
			config = PagingConfig(
				pageSize = ITEMS_IN_HOME,
				maxSize = ITEMS_IN_HOME * 3,
				prefetchDistance = DEFAULT_HOME_PREFETCH,
			),
			remoteMediator = MovieTopRatedRemoteMediator(
				movieService = movieService,
				marvinDatabase = marvinDatabase
			),
			pagingSourceFactory = pagingSourceFactory
		).flow	}

	override fun getHomeTrendingMovies(): Flow<PagingData<MovieAndTrending>> {
		val pagingSourceFactory = {
			marvinDatabase.movieTrendingDao().getHomeMovieAndTrending()
		}

		return Pager(
			config = PagingConfig(
				pageSize = ITEMS_IN_HOME,
				maxSize = ITEMS_IN_HOME * 3,
				prefetchDistance = DEFAULT_HOME_PREFETCH,
			),
			remoteMediator = MovieTrendingRemoteMediator(
				movieService = movieService,
				marvinDatabase = marvinDatabase
			),
			pagingSourceFactory = pagingSourceFactory
		).flow	}

	///////////////////////////////////////////////////////////////////////////
	// Details
	///////////////////////////////////////////////////////////////////////////

	override suspend fun getMovieDetails(id: Int): Response<SingleMovieApiResponse> {
		return movieService.getMovieDetails(id = id)
	}

	override suspend fun getMovieCollection(id: Int): Response<MovieCollection> {
		return movieService.getMovieCollection(id = id)
	}

	///////////////////////////////////////////////////////////////////////////
	// Search
	///////////////////////////////////////////////////////////////////////////

	override fun searchMovies(): Flow<PagingData<Movie>> {
		TODO("Not yet implemented")
	}

	override suspend fun saveMovieGenres() {
		val response = movieService.getMovieGenres()
		marvinDatabase.genreDao().insertMovieGenres(genres = response.movieGenres)
	}
}