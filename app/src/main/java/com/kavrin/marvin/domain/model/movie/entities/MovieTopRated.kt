package com.kavrin.marvin.domain.model.movie.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.MOVIE_TOP_RATED_TABLE


@Entity(tableName = MOVIE_TOP_RATED_TABLE)
data class MovieTopRated(
	@PrimaryKey(autoGenerate = false)
	val topRatedMovieId: Int
)

