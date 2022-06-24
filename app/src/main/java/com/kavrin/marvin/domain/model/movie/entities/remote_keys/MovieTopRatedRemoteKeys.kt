package com.kavrin.marvin.domain.model.movie.entities.remote_keys

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.MOVIE_TOP_RATED_REMOTE_TABLE

@Entity(tableName = MOVIE_TOP_RATED_REMOTE_TABLE, indices = [Index(value = ["movieTopRatedId"], unique = true),])
data class MovieTopRatedRemoteKeys(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val movieTopRatedId: Int,
	val prevPage: Int?,
	val nextPage: Int?,
	val lastUpdated: Long
)
