package com.kavrin.marvin.domain.model.tv.entities.remote_keys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.TV_POPULAR_REMOTE_TABLE

@Entity(tableName = TV_POPULAR_REMOTE_TABLE)
data class TvPopularRemoteKey(
	@PrimaryKey(autoGenerate = false)
	val tvPopularId: Int,
	val prevPage: Int?,
	val nextPage: Int?
)
