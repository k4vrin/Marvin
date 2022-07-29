package com.kavrin.marvin.domain.model.person


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonMovieCast(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("character")
    val character: String,
    @SerialName("credit_id")
    val creditId: String,
    @SerialName("id")
    val id: Int,
    @SerialName("order")
    val order: Int,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("title")
    val title: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)