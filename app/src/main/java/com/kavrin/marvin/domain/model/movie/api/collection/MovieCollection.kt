package com.kavrin.marvin.domain.model.movie.api.collection


import com.kavrin.marvin.domain.model.movie.entities.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCollection(
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("parts")
    val parts: List<Movie>?,
    @SerialName("poster_path")
    val posterPath: String?
)