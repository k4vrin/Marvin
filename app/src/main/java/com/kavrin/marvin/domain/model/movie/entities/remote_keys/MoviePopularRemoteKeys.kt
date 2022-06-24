package com.kavrin.marvin.domain.model.movie.entities.remote_keys

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.MOVIE_POPULAR_REMOTE_TABLE

@Entity(tableName = MOVIE_POPULAR_REMOTE_TABLE, indices = [Index(value = ["moviePopularId"], unique = true),])
data class MoviePopularRemoteKeys(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val moviePopularId: Int,
	val prevPage: Int?,
	val nextPage: Int?,
	val lastUpdated: Long
)
