package com.kavrin.marvin.data.paging_source.series

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.data.remote.TMDBTvService
import com.kavrin.marvin.domain.model.tv.entities.TvPopular
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular
import com.kavrin.marvin.domain.model.tv.entities.remote_keys.TvPopularRemoteKeys
import javax.inject.Inject

class TvPopularRemoteMediator @Inject constructor(
	private val tvService: TMDBTvService,
	private val marvinDatabase: MarvinDatabase
) : RemoteMediator<Int, TvAndPopular>() {

	private val tvDao = marvinDatabase.tvDao()
	private val tvPopularDao = marvinDatabase.tvPopularDao()
	private val tvPopularRemoteKeysDao = marvinDatabase.tvPopularRemoteKeysDao()


	override suspend fun load(
		loadType: LoadType,
		state: PagingState<Int, TvAndPopular>,
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

			val response = tvService.getPopularTvs(page = currentPage)

			if (response.tvs.isNotEmpty()) {
				marvinDatabase.withTransaction {

					if (loadType == LoadType.REFRESH) {
						tvPopularDao.deleteAllPopular()
						tvPopularRemoteKeysDao.deleteAllPopularRemoteKeys()
					}

					val prevPage = when (response.page) {
						0 -> null
						else -> response.page - 1
					}

					val nextPage = when (response.page) {
						response.totalPages -> null
						else -> response.page + 1
					}

					val keys = response.tvs.map { tv ->
						TvPopularRemoteKeys(
							tvPopularId = tv.tvId,
							prevPage = prevPage,
							nextPage = nextPage
						)
					}

					tvPopularRemoteKeysDao.addPopularRemoteKeys(popularRemoteKeys = keys)

					response.tvs.map { tv ->
						tvPopularDao.insertPopular(
							TvPopular(popularTvId = tv.tvId)
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
		state: PagingState<Int, TvAndPopular>,
	): TvPopularRemoteKeys? {

		return state.anchorPosition?.let { position ->
			state.closestItemToPosition(anchorPosition = position)?.tvPopular?.popularTvId?.let { id ->
				tvPopularRemoteKeysDao.getPopularRemoteKeys(id = id)
			}
		}
	}

	private suspend fun getRemoteKeyForFirstItem(
		state: PagingState<Int, TvAndPopular>,
	): TvPopularRemoteKeys? {
		return state.pages.firstOrNull { page ->
			page.data.isNotEmpty()
		}?.data?.firstOrNull()
			?.let {
				it.tvPopular?.let { it1 -> tvPopularRemoteKeysDao.getPopularRemoteKeys(id = it1.popularTvId) }
			}
	}

	private suspend fun getRemoteKeyForLastItem(
		state: PagingState<Int, TvAndPopular>,
	): TvPopularRemoteKeys? {
		return state.pages.lastOrNull {
			it.data.isNotEmpty()
		}?.data?.lastOrNull()
			?.let {
				it.tvPopular?.let { it1 -> tvPopularRemoteKeysDao.getPopularRemoteKeys(id = it1.popularTvId) }
			}
	}


}