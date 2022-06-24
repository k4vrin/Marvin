package com.kavrin.marvin.domain.model.tv.entities


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.TV_POPULAR_TABLE

@Entity(tableName = TV_POPULAR_TABLE, indices = [Index(value = ["popularTvId"], unique = true),])
data class TvPopular(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val popularTvId: Int
)