package com.kavrin.marvin.data.repository.impl

import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.repository.LocalDataSource

class LocalDataSourceImpl(
    private val marvinDatabase: MarvinDatabase
) : LocalDataSource {

    override suspend fun getMovie(movieId: Int): Movie {
        return marvinDatabase.movieDao().getSelectedMovie(movieId = movieId)
    }

    override suspend fun getTv(tvId: Int): Tv {
        return marvinDatabase.tvDao().getSelectedTv(tvId = tvId)
    }

    override suspend fun getTvGenres(ids: List<Int>): List<String> {
        val list = mutableListOf<String>()
        ids.forEach { id ->
            list.add(marvinDatabase.genreDao().getTvGenre(id = id))
        }
        return list
    }

    override suspend fun getMovieGenres(ids: List<Int>): List<String> {
        val list = mutableListOf<String>()
        ids.forEach { id ->
            list.add(marvinDatabase.genreDao().getMovieGenre(id = id))
        }
        return list
    }

    override suspend fun deleteAllMovies() {
        marvinDatabase.movieDao().deleteAllMovies()
    }

    override suspend fun deleteAllTvs() {
        marvinDatabase.tvDao().deleteAllTvs()
    }
}