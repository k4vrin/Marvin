package com.kavrin.marvin.domain.repository

import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.tv.entities.Tv

interface LocalDataSource {

    suspend fun saveMovies(movies: List<Movie>)
    suspend fun saveTvs(tvs: List<Tv>)
    suspend fun getMovie(movieId: Int): Movie
    suspend fun getTv(tvId: Int): Tv
    suspend fun getTvGenres(ids: List<Int>): List<String>
    suspend fun getMovieGenres(ids: List<Int>): List<String>
    suspend fun deleteAllMovies()
    suspend fun deleteAllTvs()
}