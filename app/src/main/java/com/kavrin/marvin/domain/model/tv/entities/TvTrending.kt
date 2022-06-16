package com.kavrin.marvin.domain.model.tv.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.TV_TRENDING_TABLE
import kotlinx.serialization.Serializable

@Entity(tableName = TV_TRENDING_TABLE)
@Serializable
data class TvTrending(
	@PrimaryKey(autoGenerate = false)
	val trendingTvId: Int
)
