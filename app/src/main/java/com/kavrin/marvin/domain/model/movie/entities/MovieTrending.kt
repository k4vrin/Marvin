package com.kavrin.marvin.domain.model.movie.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.MOVIE_TRENDING_TABLE


@Entity(tableName = MOVIE_TRENDING_TABLE)
data class MovieTrending(
	@PrimaryKey(autoGenerate = false)
	val trendingMovieId: Int
)
