package com.kavrin.marvin.domain.model.imdb


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IMDbRatingApiResponse(
    @SerialName("errorMessage")
    val errorMessage: String?,
    @SerialName("filmAffinity")
    val filmAffinity: String?,
    @SerialName("fullTitle")
    val fullTitle: String?,
    @SerialName("imDb")
    val imDb: String?,
    @SerialName("imDbId")
    val imDbId: String?,
    @SerialName("metacritic")
    val metacritic: String?,
    @SerialName("rottenTomatoes")
    val rottenTomatoes: String?,
    @SerialName("theMovieDb")
    val theMovieDb: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("year")
    val year: String?
)