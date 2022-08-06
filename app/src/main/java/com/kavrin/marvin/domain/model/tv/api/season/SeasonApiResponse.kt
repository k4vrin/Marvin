package com.kavrin.marvin.domain.model.tv.api.season


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeasonApiResponse(
    @SerialName("air_date")
    val airDate: String,
    @SerialName("episodes")
    val episodes: List<Episode>,
    @SerialName("_id")
    val _id: String,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("season_number")
    val seasonNumber: Int
)