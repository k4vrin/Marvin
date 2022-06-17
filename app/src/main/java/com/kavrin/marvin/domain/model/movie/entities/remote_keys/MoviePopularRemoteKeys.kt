package com.kavrin.marvin.domain.model.movie.entities.remote_keys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.MOVIE_POPULAR_REMOTE_TABLE

@Entity(tableName = MOVIE_POPULAR_REMOTE_TABLE)
data class MoviePopularRemoteKeys(
	@PrimaryKey(autoGenerate = false)
	val moviePopularId: Int,
	val prevPage: Int?,
	val nextPage: Int?
)
