package com.kavrin.marvin.domain.model.tv.entities


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.TV_TRENDING_TABLE
import kotlinx.serialization.Serializable

@Entity(tableName = TV_TRENDING_TABLE, indices = [Index(value = ["trendingTvId"], unique = true),])
@Serializable
data class TvTrending(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val trendingTvId: Int
)
