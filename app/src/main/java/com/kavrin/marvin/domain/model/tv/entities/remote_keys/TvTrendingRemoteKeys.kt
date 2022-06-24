package com.kavrin.marvin.domain.model.tv.entities.remote_keys

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.TV_TRENDING_REMOTE_TABLE

@Entity(tableName = TV_TRENDING_REMOTE_TABLE, indices = [Index(value = ["tvTrendingId"], unique = true),])
data class TvTrendingRemoteKeys(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val tvTrendingId: Int,
	val prevPage: Int?,
	val nextPage: Int?,
	val lastUpdated: Long
)
