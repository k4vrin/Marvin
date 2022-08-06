package com.kavrin.marvin.domain.model.tv.api.season


import com.kavrin.marvin.domain.model.common.Cast
import com.kavrin.marvin.domain.model.common.Crew
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    @SerialName("air_date")
    val airDate: String,
    @SerialName("crew")
    val crew: List<Crew>,
    @SerialName("episode_number")
    val episodeNumber: Int,
    @SerialName("guest_stars")
    val guestStars: List<Cast>,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("production_code")
    val productionCode: String,
    @SerialName("runtime")
    val runtime: Int?,
    @SerialName("season_number")
    val seasonNumber: Int,
    @SerialName("show_id")
    val showId: Int,
    @SerialName("still_path")
    val stillPath: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)