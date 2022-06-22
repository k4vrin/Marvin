package com.kavrin.marvin.data.paging_source.series

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.data.remote.TMDBTvService
import com.kavrin.marvin.domain.model.tv.entities.TvTrending
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending
import com.kavrin.marvin.domain.model.tv.entities.remote_keys.TvTrendingRemoteKeys
import javax.inject.Inject

class TvTrendingRemoteMediator @Inject constructor(
	private val tvService: TMDBTvService,
	private val marvinDatabase: MarvinDatabase
) : RemoteMediator<Int, TvAndTrending>() {

	private val tvDao = marvinDatabase.tvDao()
	private val tvTrendingDao = marvinDatabase.tvTrendingDao()
	private val tvTrendingRemoteKeysDao = marvinDatabase.tvTrendingRemoteKeysDao()


	override suspend fun load(
		loadType: LoadType,
		state: PagingState<Int, TvAndTrending>,
	): MediatorResult {
		return try {

			val currentPage: Int = when (loadType) {
				LoadType.REFRESH -> {
					val remoteKeys = getRemoteKeysClosestToCurrentPosition(state)
					remoteKeys?.nextPage?.minus(1) ?: 1
				}
				LoadType.PREPEND -> {
					val remoteKeys = getRemoteKeyForFirstItem(state)
					val prevPage = remoteKeys?.prevPage
						?: return MediatorResult.Success(
							endOfPaginationReached = remoteKeys != null
						)
					prevPage
				}
				LoadType.APPEND -> {
					val remoteKeys = getRemoteKeyForLastItem(state)
					val nextPage = remoteKeys?.nextPage
						?:return MediatorResult.Success(
							endOfPaginationReached = remoteKeys != null
						)
					nextPage
				}
			}

			val response = tvService.getTrendingTvs(page = currentPage)

			if (response.tvs.isNotEmpty()) {
				marvinDatabase.withTransaction {

					if (loadType == LoadType.REFRESH) {
						tvTrendingDao.deleteAllTrending()
						tvTrendingRemoteKeysDao.deleteAllTrendingRemoteKeys()
					}

					val prevPage = when (response.page) {
						1 -> null
						else -> response.page - 1
					}

					val nextPage = when (response.page) {
						response.totalPages -> null
						else -> response.page + 1
					}

					val keys = response.tvs.map { tv ->
						TvTrendingRemoteKeys(
							tvTrendingId = tv.tvId,
							prevPage = prevPage,
							nextPage = nextPage
						)
					}

					tvTrendingRemoteKeysDao.addTrendingRemoteKeys(trendingRemoteKeys = keys)

					response.tvs.map { tv ->
						tvTrendingDao.insertTrending(
							TvTrending(trendingTvId = tv.tvId)
						)
					}

					tvDao.insertTv(tv = response.tvs)

				}
			}


			MediatorResult.Success(endOfPaginationReached = response.page == response.totalPages)
		} catch (e: Exception) {
			return MediatorResult.Error(e)
		}
	}

	private suspend fun getRemoteKeysClosestToCurrentPosition(
		state: PagingState<Int, TvAndTrending>,
	): TvTrendingRemoteKeys? {

		return state.anchorPosition?.let { position ->
			state.closestItemToPosition(anchorPosition = position)?.tvTrending?.trendingTvId?.let { id ->
				tvTrendingRemoteKeysDao.getTrendingRemoteKeys(id = id)
			}
		}
	}

	private suspend fun getRemoteKeyForFirstItem(
		state: PagingState<Int, TvAndTrending>,
	): TvTrendingRemoteKeys? {
		return state.pages.firstOrNull { page ->
			page.data.isNotEmpty()
		}?.data?.firstOrNull()
			?.let {
				it.tvTrending?.let { it1 -> tvTrendingRemoteKeysDao.getTrendingRemoteKeys(id = it1.trendingTvId) }
			}
	}

	private suspend fun getRemoteKeyForLastItem(
		state: PagingState<Int, TvAndTrending>,
	): TvTrendingRemoteKeys? {
		return state.pages.lastOrNull {
			it.data.isNotEmpty()
		}?.data?.lastOrNull()
			?.let {
				it.tvTrending?.let { it1 -> tvTrendingRemoteKeysDao.getTrendingRemoteKeys(id = it1.trendingTvId) }
			}
	}


}