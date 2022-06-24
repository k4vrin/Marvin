package com.kavrin.marvin.domain.model.tv.entities.remote_keys

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.TV_POPULAR_REMOTE_TABLE

@Entity(tableName = TV_POPULAR_REMOTE_TABLE, indices = [Index(value = ["tvPopularId"], unique = true),])
data class TvPopularRemoteKeys(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val tvPopularId: Int,
	val prevPage: Int?,
	val nextPage: Int?,
	val lastUpdated: Long
)
