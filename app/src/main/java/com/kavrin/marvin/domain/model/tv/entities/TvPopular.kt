package com.kavrin.marvin.domain.model.tv.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.TV_POPULAR_TABLE

@Entity(tableName = TV_POPULAR_TABLE)
data class TvPopular(
	@PrimaryKey(autoGenerate = false)
	val popularTvId: Int
)