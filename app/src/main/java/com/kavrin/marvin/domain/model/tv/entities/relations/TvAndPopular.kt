package com.kavrin.marvin.domain.model.tv.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.model.tv.entities.TvPopular
import com.kavrin.marvin.util.MarvinTvItem

data class TvAndPopular(

	@Embedded
	override val tv: Tv,

	@Relation(
		parentColumn = "tvId",
		entityColumn = "popularTvId",
		entity = TvPopular::class
	)
	val tvPopular: TvPopular?
) : MarvinTvItem
