package com.kavrin.marvin.domain.model.tv.entities.remote_keys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.TV_TRENDING_REMOTE_TABLE

@Entity(tableName = TV_TRENDING_REMOTE_TABLE)
data class TvTrendingRemoteKeys(
	@PrimaryKey(autoGenerate = false)
	val tvTrendingId: Int,
	val prevPage: Int?,
	val nextPage: Int?
)
