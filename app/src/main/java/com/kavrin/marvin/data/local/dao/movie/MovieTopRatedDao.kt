package com.kavrin.marvin.data.local.dao.movie

import androidx.paging.PagingSource
import androidx.room.*
import com.kavrin.marvin.domain.model.movie.entities.MovieTopRated
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated

@Dao
interface MovieTopRatedDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTopRated(movieTopRated: MovieTopRated)

	@Query("DELETE FROM movie_top_rated_table")
	suspend fun deleteAllTopRated()

	@Transaction
	@Query("SELECT * FROM movie_table WHERE movieId = :movieId")
	fun getMovieAndTopRated(movieId: Int): PagingSource<Int, MovieAndTopRated>
}