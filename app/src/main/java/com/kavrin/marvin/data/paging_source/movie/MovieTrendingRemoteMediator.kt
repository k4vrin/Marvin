package com.kavrin.marvin.data.paging_source.movie

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.data.remote.TMDBMovieService
import com.kavrin.marvin.domain.model.movie.entities.MovieTrending
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import com.kavrin.marvin.domain.model.movie.entities.remote_keys.MovieTrendingRemoteKeys
import javax.inject.Inject

class MovieTrendingRemoteMediator @Inject constructor(
	private val movieService: TMDBMovieService,
	private val marvinDatabase: MarvinDatabase
) : RemoteMediator<Int, MovieAndTrending>() {

	private val movieDao = marvinDatabase.movieDao()
	private val movieTrendingDao = marvinDatabase.movieTrendingDao()
	private val movieTrendingRemoteKeysDao = marvinDatabase.movieTrendingRemoteKeysDao()


	override suspend fun load(
		loadType: LoadType,
		state: PagingState<Int, MovieAndTrending>,
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

			val response = movieService.getTrendingMovies(page = currentPage)

			if (response.movies.isNotEmpty()) {
				marvinDatabase.withTransaction {

					if (loadType == LoadType.REFRESH) {
						movieTrendingDao.deleteAllTrending()
						movieTrendingRemoteKeysDao.deleteAllTrendingRemoteKeys()
					}

					val prevPage = when (response.page) {
						0 -> null
						else -> response.page - 1
					}

					val nextPage = when (response.page) {
						response.totalPages -> null
						else -> response.page + 1
					}

					val keys = response.movies.map { movie ->
						MovieTrendingRemoteKeys(
							movieTrendingId = movie.movieId,
							prevPage = prevPage,
							nextPage = nextPage
						)
					}

					movieTrendingRemoteKeysDao.addTrendingRemoteKeys(trendingRemoteKeys = keys)

					response.movies.map { movie ->
						movieTrendingDao.insertTrending(
							MovieTrending(trendingMovieId = movie.movieId)
						)
					}

					movieDao.insertMovie(movie = response.movies)

				}
			}


			MediatorResult.Success(endOfPaginationReached = response.page == response.totalPages)
		} catch (e: Exception) {
			return MediatorResult.Error(e)
		}
	}

	private suspend fun getRemoteKeysClosestToCurrentPosition(
		state: PagingState<Int, MovieAndTrending>,
	): MovieTrendingRemoteKeys? {

		return state.anchorPosition?.let { position ->
			state.closestItemToPosition(anchorPosition = position)?.movieTrending?.trendingMovieId?.let { id ->
				movieTrendingRemoteKeysDao.getTrendingRemoteKeys(id = id)
			}
		}
	}

	private suspend fun getRemoteKeyForFirstItem(
		state: PagingState<Int, MovieAndTrending>,
	): MovieTrendingRemoteKeys? {
		return state.pages.firstOrNull { page ->
			page.data.isNotEmpty()
		}?.data?.firstOrNull()
			?.let {
				movieTrendingRemoteKeysDao.getTrendingRemoteKeys(id = it.movieTrending.trendingMovieId)
			}
	}

	private suspend fun getRemoteKeyForLastItem(
		state: PagingState<Int, MovieAndTrending>,
	): MovieTrendingRemoteKeys? {
		return state.pages.lastOrNull {
			it.data.isNotEmpty()
		}?.data?.lastOrNull()
			?.let {
				movieTrendingRemoteKeysDao.getTrendingRemoteKeys(id = it.movieTrending.trendingMovieId)
			}
	}


}