package com.kavrin.marvin.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.data.paging_source.movie.MoviePopularRemoteMediator
import com.kavrin.marvin.data.paging_source.movie.MovieTopRatedRemoteMediator
import com.kavrin.marvin.data.paging_source.movie.MovieTrendingRemoteMediator
import com.kavrin.marvin.data.remote.TMDBMovieService
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import com.kavrin.marvin.domain.repository.MovieRemoteDataSource
import com.kavrin.marvin.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

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
			marvinDatabase.movieTrendingDao().getCarouselMovieAndTrending()
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

	override fun getHomePopularMovies(): Flow<PagingData<MovieAndPopular>> {
		val pagingSourceFactory = {
			marvinDatabase.moviePopularDao().getHomeMovieAndPopular()
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

	override fun getHomeTopRatedMovies(): Flow<PagingData<MovieAndTopRated>> {
		val pagingSourceFactory = {
			marvinDatabase.movieTopRatedDao().getHomeMovieAndTopRated()
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

	override fun getHomeTrendingMovies(): Flow<PagingData<MovieAndTrending>> {
		val pagingSourceFactory = {
			marvinDatabase.movieTrendingDao().getHomeMovieAndTrending()
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
	// Search
	///////////////////////////////////////////////////////////////////////////

	override fun searchMovies(): Flow<PagingData<Movie>> {
		TODO("Not yet implemented")
	}


}