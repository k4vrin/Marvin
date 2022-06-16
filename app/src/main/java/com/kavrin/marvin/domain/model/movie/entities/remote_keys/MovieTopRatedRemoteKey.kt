package com.kavrin.marvin.domain.model.movie.entities.remote_keys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.MOVIE_TOP_RATED_REMOTE_TABLE

@Entity(tableName = MOVIE_TOP_RATED_REMOTE_TABLE)
data class MovieTopRatedRemoteKey(
	@PrimaryKey(autoGenerate = false)
	val movieTopRatedId: Int,
	val prevPage: Int?,
	val nextPage: Int?
)
