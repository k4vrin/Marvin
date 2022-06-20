package com.kavrin.marvin.domain.model.tv.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.model.tv.entities.TvTrending

data class TvAndTrending(

	@Embedded
	val tv: Tv?,

	@Relation(
		parentColumn = "tvId",
		entityColumn = "trendingTvId",
		entity = TvTrending::class
	)
	val tvTrending: TvTrending?
)
