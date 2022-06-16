package com.kavrin.marvin.domain.model.tv.api


import com.kavrin.marvin.domain.model.tv.entities.Tv
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvApiResponse(

	@SerialName("page")
    val page: Int,

	@SerialName("results")
    val movies: List<Tv>,

	@SerialName("total_pages")
    val totalPages: Int,

	@SerialName("total_results")
    val totalResults: Int
)