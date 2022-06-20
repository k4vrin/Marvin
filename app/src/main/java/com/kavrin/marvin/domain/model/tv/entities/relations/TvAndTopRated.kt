package com.kavrin.marvin.domain.model.tv.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.model.tv.entities.TvTopRated

data class TvAndTopRated(

	@Embedded
	val tv: Tv?,

	@Relation(
		parentColumn = "tvId",
		entityColumn = "topRatedTvId",
		entity = TvTopRated::class
	)
	val tvTopRated: TvTopRated?
)
