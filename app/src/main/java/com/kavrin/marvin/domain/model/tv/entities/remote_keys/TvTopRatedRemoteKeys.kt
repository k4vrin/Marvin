package com.kavrin.marvin.domain.model.tv.entities.remote_keys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.TV_TOP_RATED_REMOTE_TABLE

@Entity(tableName = TV_TOP_RATED_REMOTE_TABLE)
data class TvTopRatedRemoteKeys(
	@PrimaryKey(autoGenerate = false)
	val tvTopRatedId: Int,
	val prevPage: Int?,
	val nextPage: Int?
)
