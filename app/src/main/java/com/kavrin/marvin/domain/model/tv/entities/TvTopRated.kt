package com.kavrin.marvin.domain.model.tv.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.TV_TOP_RATED_TABLE

@Entity(tableName = TV_TOP_RATED_TABLE)
data class TvTopRated(

	@PrimaryKey(autoGenerate = false)
	val topRatedTvId: Int
)
