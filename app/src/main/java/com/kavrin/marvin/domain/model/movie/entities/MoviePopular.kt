package com.kavrin.marvin.domain.model.movie.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.MOVIE_POPULAR_TABLE

@Entity(tableName = MOVIE_POPULAR_TABLE, indices = [Index(value = ["popularMovieId"], unique = true),])
data class MoviePopular(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val popularMovieId: Int
)