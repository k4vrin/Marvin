package com.kavrin.marvin.domain.model.movie.entities.remote_keys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.MOVIE_TRENDING_REMOTE_TABLE

@Entity(tableName = MOVIE_TRENDING_REMOTE_TABLE)
data class MovieTrendingRemoteKeys(
	@PrimaryKey(autoGenerate = false)
	val movieTrendingId: Int,
	val prevPage: Int?,
	val nextPage: Int?
)
