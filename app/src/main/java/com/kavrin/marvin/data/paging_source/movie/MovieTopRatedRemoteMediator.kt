package com.kavrin.marvin.data.paging_source.movie

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.data.remote.TMDBMovieService
import com.kavrin.marvin.domain.model.movie.entities.MovieTopRated
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import com.kavrin.marvin.domain.model.movie.entities.remote_keys.MovieTopRatedRemoteKeys
import javax.inject.Inject

class MovieTopRatedRemoteMediator @Inject constructor(
	private val movieService: TMDBMovieService,
	private val marvinDatabase: MarvinDatabase
) : RemoteMediator<Int, MovieAndTopRated>() {

	private val movieDao = marvinDatabase.movieDao()
	private val movieTopRatedDao = marvinDatabase.movieTopRatedDao()
	private val movieTopRatedRemoteKeysDao = marvinDatabase.movieTopRatedRemoteKeysDao()


	override suspend fun load(
		loadType: LoadType,
		state: PagingState<Int, MovieAndTopRated>,
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

			val response = movieService.getTopRatedMovies(page = currentPage)

			if (response.movies.isNotEmpty()) {
				marvinDatabase.withTransaction {

					if (loadType == LoadType.REFRESH) {
						movieTopRatedDao.deleteAllTopRated()
						movieTopRatedRemoteKeysDao.deleteAllTopRatedRemoteKeys()
					}

					val prevPage = when (response.page) {
						1 -> null
						else -> response.page - 1
					}

					val nextPage = when (response.page) {
						response.totalPages -> null
						else -> response.page + 1
					}

					val keys = response.movies.map { movie ->
						MovieTopRatedRemoteKeys(
							movieTopRatedId = movie.movieId,
							prevPage = prevPage,
							nextPage = nextPage
						)
					}

					movieTopRatedRemoteKeysDao.addTopRatedRemoteKeys(topRatedRemoteKeys = keys)

					response.movies.map { movie ->
						movieTopRatedDao.insertTopRated(
							MovieTopRated(topRatedMovieId = movie.movieId)
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
		state: PagingState<Int, MovieAndTopRated>,
	): MovieTopRatedRemoteKeys? {

		return state.anchorPosition?.let { position ->
			state.closestItemToPosition(anchorPosition = position)?.movieTopRated?.topRatedMovieId?.let { id ->
				movieTopRatedRemoteKeysDao.getTopRatedRemoteKeys(id = id)
			}
		}
	}

	private suspend fun getRemoteKeyForFirstItem(
		state: PagingState<Int, MovieAndTopRated>,
	): MovieTopRatedRemoteKeys? {
		return state.pages.firstOrNull { page ->
			page.data.isNotEmpty()
		}?.data?.firstOrNull()
			?.let {
				it.movieTopRated?.let { it1 -> movieTopRatedRemoteKeysDao.getTopRatedRemoteKeys(id = it1.topRatedMovieId) }
			}
	}

	private suspend fun getRemoteKeyForLastItem(
		state: PagingState<Int, MovieAndTopRated>,
	): MovieTopRatedRemoteKeys? {
		return state.pages.lastOrNull {
			it.data.isNotEmpty()
		}?.data?.lastOrNull()
			?.let {
				it.movieTopRated?.let { it1 -> movieTopRatedRemoteKeysDao.getTopRatedRemoteKeys(id = it1.topRatedMovieId) }
			}
	}


}