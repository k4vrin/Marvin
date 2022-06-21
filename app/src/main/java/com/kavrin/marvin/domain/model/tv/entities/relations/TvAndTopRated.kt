package com.kavrin.marvin.domain.model.tv.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.model.tv.entities.TvTopRated
import com.kavrin.marvin.util.MarvinItem

data class TvAndTopRated(

	@Embedded
	override val tv: Tv?,

	@Relation(
		parentColumn = "tvId",
		entityColumn = "topRatedTvId",
		entity = TvTopRated::class
	)
	val tvTopRated: TvTopRated?
) : MarvinItem
