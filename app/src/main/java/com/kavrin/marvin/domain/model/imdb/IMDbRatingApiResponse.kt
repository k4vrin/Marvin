package com.kavrin.marvin.domain.model.imdb


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IMDbRatingApiResponse(
    @SerialName("errorMessage")
    val errorMessage: String,
    @SerialName("imDb")
    val imDb: String?,
    @SerialName("metacritic")
    val metacritic: String?,
    @SerialName("rottenTomatoes")
    val rottenTomatoes: String?,
    @SerialName("theMovieDb")
    val theMovieDb: String?,
)