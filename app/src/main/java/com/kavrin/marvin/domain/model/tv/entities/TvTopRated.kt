package com.kavrin.marvin.domain.model.tv.entities


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.TV_TOP_RATED_TABLE

@Entity(tableName = TV_TOP_RATED_TABLE, indices = [Index(value = ["topRatedTvId"], unique = true),])
data class TvTopRated(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val topRatedTvId: Int
)
