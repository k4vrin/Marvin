package com.kavrin.marvin.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.data.paging_source.series.TvPopularRemoteMediator
import com.kavrin.marvin.data.paging_source.series.TvTopRatedRemoteMediator
import com.kavrin.marvin.data.paging_source.series.TvTrendingRemoteMediator
import com.kavrin.marvin.data.remote.TMDBTvService
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending
import com.kavrin.marvin.domain.repository.TvRemoteDataSource
import com.kavrin.marvin.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

class TvRemoteDataSourceImpl(
	private val tvService: TMDBTvService,
	private val marvinDatabase: MarvinDatabase,
) : TvRemoteDataSource {

	///////////////////////////////////////////////////////////////////////////
	// All
	///////////////////////////////////////////////////////////////////////////

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

	///////////////////////////////////////////////////////////////////////////
	// Home
	///////////////////////////////////////////////////////////////////////////

	override fun getCarouselTvs(): Flow<PagingData<TvAndTrending>> {
		val pagingSourceFactory = {
			marvinDatabase.tvTrendingDao().getCarouselTvs()
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

	override fun getHomePopularTvs(): Flow<PagingData<TvAndPopular>> {
		val pagingSourceFactory = {
			marvinDatabase.tvPopularDao().getHomeTvAndPopular()
		}

		return Pager(
			config = PagingConfig(pageSize = ITEMS_PER_PAGE),
			remoteMediator = TvPopularRemoteMediator(
				tvService = tvService,
				marvinDatabase = marvinDatabase
			),
			pagingSourceFactory = pagingSourceFactory
		).flow	}

	override fun getHomeTopRatedTvs(): Flow<PagingData<TvAndTopRated>> {
		val pagingSourceFactory = {
			marvinDatabase.tvTopRatedDao().getHomeTvAndTopRated()
		}

		return Pager(
			config = PagingConfig(pageSize = ITEMS_PER_PAGE),
			remoteMediator = TvTopRatedRemoteMediator(
				tvService = tvService,
				marvinDatabase = marvinDatabase
			),
			pagingSourceFactory = pagingSourceFactory
		).flow	}

	override fun getHomeTrendingTvs(): Flow<PagingData<TvAndTrending>> {
		val pagingSourceFactory = {
			marvinDatabase.tvTrendingDao().getHomeTvAndTrending()
		}

		return Pager(
			config = PagingConfig(pageSize = ITEMS_PER_PAGE),
			remoteMediator = TvTrendingRemoteMediator(
				tvService = tvService,
				marvinDatabase = marvinDatabase
			),
			pagingSourceFactory = pagingSourceFactory
		).flow	}

	///////////////////////////////////////////////////////////////////////////
	// Search
	///////////////////////////////////////////////////////////////////////////

	override fun searchTvs(): Flow<PagingData<Tv>> {
		TODO("Not yet implemented")
	}

	override suspend fun saveTvGenres() {
		val response = tvService.getTvGenres()
		marvinDatabase.genreDao().insertTvGenres(genres = response.tvGenres)
	}
}