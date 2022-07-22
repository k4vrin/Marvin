package com.kavrin.marvin.domain.model.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExternalIds(
    @SerialName("facebook_id")
    val facebookId: String?,
    @SerialName("imdb_id")
    val imdbId: String?,
    @SerialName("instagram_id")
    val instagramId: String?,
    @SerialName("tvdb_id")
    val tvdbId: Int?,
    @SerialName("tvrage_id")
    val tvrageId: Int?,
    @SerialName("twitter_id")
    val twitterId: String?
)