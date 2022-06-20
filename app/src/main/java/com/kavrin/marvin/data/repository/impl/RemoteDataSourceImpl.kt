package com.kavrin.marvin.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.data.paging_source.movie.MoviePopularRemoteMediator
import com.kavrin.marvin.data.paging_source.movie.MovieTopRatedRemoteMediator
import com.kavrin.marvin.data.paging_source.movie.MovieTrendingRemoteMediator
import com.kavrin.marvin.data.paging_source.series.TvPopularRemoteMediator
import com.kavrin.marvin.data.paging_source.series.TvTopRatedRemoteMediator
import com.kavrin.marvin.data.paging_source.series.TvTrendingRemoteMediator
import com.kavrin.marvin.data.remote.TMDBMovieService
import com.kavrin.marvin.data.remote.TMDBTvService
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending
import com.kavrin.marvin.domain.repository.RemoteDataSource
import com.kavrin.marvin.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
	private val movieService: TMDBMovieService,
	private val tvService: TMDBTvService,
	private val marvinDatabase: MarvinDatabase,
) : RemoteDataSource {

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

	override fun getCarouselMovies(): Flow<PagingData<MovieAndTrending>> {
		val pagingSourceFactory = {
			marvinDatabase.movieTrendingDao().getMovieAndTrendingCarousel()
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

	override fun getPopularTvs(): Flow<PagingData<TvAndPopular>> {
		val pagingSourceFactory = {
			marvinDatabase.tvPopularDao().getTvAndPopular()
		}

		return Pager(
			config = PagingConfig(pageSize = ITEMS_PER_PAGE),
			remoteMediator = TvPopularRemoteMediator(
				tvService = tvService,
				marvinDatabase = marvinDatabase
			),
			pagingSourceFactory = pagingSourceFactory
		).flow
	}

	override fun getTopRatedTvs(): Flow<PagingData<TvAndTopRated>> {
		val pagingSourceFactory = {
			marvinDatabase.tvTopRatedDao().getTvAndTopRated()
		}

		return Pager(
			config = PagingConfig(pageSize = ITEMS_PER_PAGE),
			remoteMediator = TvTopRatedRemoteMediator(
				tvService = tvService,
				marvinDatabase = marvinDatabase
			),
			pagingSourceFactory = pagingSourceFactory
		).flow
	}

	override fun getTrendingTvs(): Flow<PagingData<TvAndTrending>> {
		val pagingSourceFactory = {
			marvinDatabase.tvTrendingDao().getTvAndTrending()
		}

		return Pager(
			config = PagingConfig(pageSize = ITEMS_PER_PAGE),
			remoteMediator = TvTrendingRemoteMediator(
				tvService = tvService,
				marvinDatabase = marvinDatabase
			),
			pagingSourceFactory = pagingSourceFactory
		).flow
	}


	override fun searchMovies(): Flow<PagingData<Movie>> {
		TODO("Not yet implemented")
	}

	override fun searchTvs(): Flow<PagingData<Tv>> {
		TODO("Not yet implemented")
	}


}