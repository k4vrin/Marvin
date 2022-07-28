package com.kavrin.marvin.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.data.paging_source.series.SearchTvSource
import com.kavrin.marvin.data.paging_source.series.TvPopularRemoteMediator
import com.kavrin.marvin.data.paging_source.series.TvTopRatedRemoteMediator
import com.kavrin.marvin.data.paging_source.series.TvTrendingRemoteMediator
import com.kavrin.marvin.data.remote.TMDBTvService
import com.kavrin.marvin.domain.model.tv.api.detail.SingleTvApiResponse
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending
import com.kavrin.marvin.domain.repository.TvRemoteDataSource
import com.kavrin.marvin.util.Constants.DEFAULT_HOME_PREFETCH
import com.kavrin.marvin.util.Constants.ITEMS_IN_HOME
import com.kavrin.marvin.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class TvRemoteDataSourceImpl(
	private val tvService: TMDBTvService,
	private val marvinDatabase: MarvinDatabase,
) : TvRemoteDataSource {

	///////////////////////////////////////////////////////////////////////////
	// list
	///////////////////////////////////////////////////////////////////////////

	override fun getPopularTvs(): Flow<PagingData<TvAndPopular>> {
		val pagingSourceFactory = {
			marvinDatabase.tvPopularDao().getTvAndPopular()
		}

		return Pager(
			config = PagingConfig(
				pageSize = ITEMS_PER_PAGE,
				prefetchDistance = ITEMS_PER_PAGE * 2
			),
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
			config = PagingConfig(
				pageSize = ITEMS_PER_PAGE,
				prefetchDistance = ITEMS_PER_PAGE * 2
			),
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
			config = PagingConfig(
				pageSize = ITEMS_PER_PAGE,
				prefetchDistance = ITEMS_PER_PAGE * 2
			),
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
			config = PagingConfig(
				pageSize = ITEMS_IN_HOME,
				maxSize = ITEMS_IN_HOME * 3,
				prefetchDistance = DEFAULT_HOME_PREFETCH,
			),
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
			config = PagingConfig(
				pageSize = ITEMS_IN_HOME,
				maxSize = ITEMS_IN_HOME * 3,
				prefetchDistance = DEFAULT_HOME_PREFETCH,
			),
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
			config = PagingConfig(
				pageSize = ITEMS_IN_HOME,
				maxSize = ITEMS_IN_HOME * 3,
				prefetchDistance = DEFAULT_HOME_PREFETCH,
			),
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
			config = PagingConfig(
				pageSize = ITEMS_IN_HOME,
				maxSize = ITEMS_IN_HOME * 3,
				prefetchDistance = DEFAULT_HOME_PREFETCH,
			),
			remoteMediator = TvTrendingRemoteMediator(
				tvService = tvService,
				marvinDatabase = marvinDatabase
			),
			pagingSourceFactory = pagingSourceFactory
		).flow	}

	///////////////////////////////////////////////////////////////////////////
	// Details
	///////////////////////////////////////////////////////////////////////////

	override suspend fun getTvDetails(id: Int): Response<SingleTvApiResponse> {
		return tvService.getTvDetails(id = id)
	}

	///////////////////////////////////////////////////////////////////////////
	// Search
	///////////////////////////////////////////////////////////////////////////

	override fun searchTvs(query: String): Flow<PagingData<Tv>> {
			return Pager(
				config = PagingConfig(
					pageSize = ITEMS_PER_PAGE,
					enablePlaceholders = false
				),
				pagingSourceFactory = {
					SearchTvSource(
						tvService = tvService,
						query = query
					)
				}
			).flow
	}

	override suspend fun saveTvGenres() {
		val response = tvService.getTvGenres()
		marvinDatabase.genreDao().insertTvGenres(genres = response.tvGenres)
	}
}