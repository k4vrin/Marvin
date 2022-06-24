package com.kavrin.marvin.domain.model.movie.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.MOVIE_TOP_RATED_TABLE


@Entity(tableName = MOVIE_TOP_RATED_TABLE, indices = [Index(value = ["topRatedMovieId"], unique = true),])
data class MovieTopRated(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val topRatedMovieId: Int
)

