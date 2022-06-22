package com.kavrin.marvin.data.local.dao.movie

import androidx.paging.PagingSource
import androidx.room.*
import com.kavrin.marvin.domain.model.movie.entities.MoviePopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular

@Dao
interface MoviePopularDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertPopular(moviePopular: MoviePopular)

	@Query("DELETE FROM movie_popular_table")
	suspend fun deleteAllPopular()

	@Transaction
	@Query("SELECT * FROM movie_table")
	fun getMovieAndPopular(): PagingSource<Int, MovieAndPopular>

	@Transaction
	@Query("SELECT * FROM movie_table, movie_popular_table WHERE movieId = popularMovieId LIMIT 8")
	fun getHomeMovieAndPopular(): PagingSource<Int, MovieAndPopular>

}