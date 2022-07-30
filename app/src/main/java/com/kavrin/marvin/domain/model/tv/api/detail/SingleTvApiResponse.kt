package com.kavrin.marvin.domain.model.tv.api.detail


import com.kavrin.marvin.domain.model.common.*
import com.kavrin.marvin.domain.model.tv.api.TvApiResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SingleTvApiResponse(
    @SerialName("created_by")
    val createdBy: List<CreatedBy>,
    @SerialName("credits")
    val credits: Credits,
    @SerialName("episode_run_time")
    val episodeRunTime: List<Int>,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("genres")
    val genres: List<Genre>,
    @SerialName("homepage")
    val homepage: String,
    @SerialName("id")
    val id: Int,
    @SerialName("images")
    val images: Images,
    @SerialName("in_production")
    val inProduction: Boolean,
    @SerialName("last_air_date")
    val lastAirDate: String,
    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: EpisodeToAir?,
    @SerialName("name")
    val name: String,
    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: EpisodeToAir?,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("seasons")
    val seasons: List<Season>,
    @SerialName("status")
    val status: String,
    @SerialName("tagline")
    val tagline: String,
    @SerialName("videos")
    val videos: Videos,
    @SerialName("external_ids")
    val externalIds: ExternalIds,
    @SerialName("reviews")
    val reviews: Reviews?,
    @SerialName("recommendations")
    val recommendations: TvApiResponse,
    @SerialName("similar")
    val similar: TvApiResponse,
    @SerialName("backdrop_path")
    val backdrop: String?,
)