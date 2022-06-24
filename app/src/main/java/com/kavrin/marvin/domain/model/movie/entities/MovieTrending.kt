package com.kavrin.marvin.domain.model.movie.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.MOVIE_TRENDING_TABLE


@Entity(tableName = MOVIE_TRENDING_TABLE, indices = [Index(value = ["trendingMovieId"], unique = true),])
data class MovieTrending(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val trendingMovieId: Int
)
