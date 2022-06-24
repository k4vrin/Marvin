package com.kavrin.marvin.data.local.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kavrin.marvin.domain.model.movie.entities.Movie

@Dao
interface MovieDao {

		@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertMovie(movie: List<Movie>)

	@Query("SELECT * FROM movie_table WHERE movieId = :movieId")
	suspend fun getSelectedMovie(movieId: Int): Movie

}