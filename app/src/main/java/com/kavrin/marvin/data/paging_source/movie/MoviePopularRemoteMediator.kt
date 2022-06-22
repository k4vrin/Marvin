package com.kavrin.marvin.data.paging_source.movie

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.data.remote.TMDBMovieService
import com.kavrin.marvin.domain.model.movie.entities.MoviePopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import com.kavrin.marvin.domain.model.movie.entities.remote_keys.MoviePopularRemoteKeys
import javax.inject.Inject

class MoviePopularRemoteMediator @Inject constructor(
	private val movieService: TMDBMovieService,
	private val marvinDatabase: MarvinDatabase
) : RemoteMediator<Int, MovieAndPopular>() {

	private val movieDao = marvinDatabase.movieDao()
	private val moviePopularDao = marvinDatabase.moviePopularDao()
	private val moviePopularRemoteKeysDao = marvinDatabase.moviePopularRemoteKeysDao()


	override suspend fun load(
		loadType: LoadType,
		state: PagingState<Int, MovieAndPopular>,
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

			val response = movieService.getPopularMovies(page = currentPage)

			if (response.movies.isNotEmpty()) {
				marvinDatabase.withTransaction {

					if (loadType == LoadType.REFRESH) {
						moviePopularDao.deleteAllPopular()
						moviePopularRemoteKeysDao.deleteAllPopularRemoteKeys()
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
						MoviePopularRemoteKeys(
							moviePopularId = movie.movieId,
							prevPage = prevPage,
							nextPage = nextPage
						)
					}

					moviePopularRemoteKeysDao.addPopularRemoteKeys(popularRemoteKeys = keys)

					response.movies.map { movie ->
						moviePopularDao.insertPopular(
							MoviePopular(popularMovieId = movie.movieId)
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
		state: PagingState<Int, MovieAndPopular>,
	): MoviePopularRemoteKeys? {

		return state.anchorPosition?.let { position ->
			state.closestItemToPosition(anchorPosition = position)?.moviePopular?.popularMovieId?.let { id ->
				moviePopularRemoteKeysDao.getPopularRemoteKeys(id = id)
			}
		}
	}

	private suspend fun getRemoteKeyForFirstItem(
		state: PagingState<Int, MovieAndPopular>,
	): MoviePopularRemoteKeys? {
		return state.pages.firstOrNull { page ->
			page.data.isNotEmpty()
		}?.data?.firstOrNull()
			?.let {
				it.moviePopular?.let { it1 -> moviePopularRemoteKeysDao.getPopularRemoteKeys(id = it1.popularMovieId) }
			}
	}

	private suspend fun getRemoteKeyForLastItem(
		state: PagingState<Int, MovieAndPopular>,
	): MoviePopularRemoteKeys? {
		return state.pages.lastOrNull {
			it.data.isNotEmpty()
		}?.data?.lastOrNull()
			?.let {
				it.moviePopular?.let { it1 -> moviePopularRemoteKeysDao.getPopularRemoteKeys(id = it1.popularMovieId) }
			}
	}


}