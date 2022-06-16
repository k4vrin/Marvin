package com.kavrin.marvin.domain.model.movie.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.MOVIE_POPULAR_TABLE

@Entity(tableName = MOVIE_POPULAR_TABLE)
data class MoviePopular(
	@PrimaryKey(autoGenerate = false)
	val popularMovieId: Int
)