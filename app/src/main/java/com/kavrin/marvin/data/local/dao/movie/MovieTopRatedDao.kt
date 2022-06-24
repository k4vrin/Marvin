package com.kavrin.marvin.data.local.dao.movie

import androidx.paging.PagingSource
import androidx.room.*
import com.kavrin.marvin.domain.model.movie.entities.MovieTopRated
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated

@Dao
interface MovieTopRatedDao {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertTopRated(movieTopRated: List<MovieTopRated>)

	@Query("DELETE FROM movie_top_rated_table")
	suspend fun deleteAllTopRated()

	@Transaction
	@Query("SELECT * FROM movie_table, movie_top_rated_remote_table WHERE movieId = movieTopRatedId")
	fun getMovieAndTopRated(): PagingSource<Int, MovieAndTopRated>

	@Transaction
	@Query("SELECT * FROM movie_table, movie_top_rated_remote_table WHERE movieId = movieTopRatedId LIMIT 8")
	fun getHomeMovieAndTopRated(): PagingSource<Int, MovieAndTopRated>
}