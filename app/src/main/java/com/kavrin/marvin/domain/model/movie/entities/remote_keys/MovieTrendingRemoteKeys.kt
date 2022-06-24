package com.kavrin.marvin.domain.model.movie.entities.remote_keys

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.MOVIE_TRENDING_REMOTE_TABLE

@Entity(tableName = MOVIE_TRENDING_REMOTE_TABLE, indices = [Index(value = ["movieTrendingId"], unique = true),])
data class MovieTrendingRemoteKeys(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val movieTrendingId: Int,
	val prevPage: Int?,
	val nextPage: Int?,
	val lastUpdated: Long
)
