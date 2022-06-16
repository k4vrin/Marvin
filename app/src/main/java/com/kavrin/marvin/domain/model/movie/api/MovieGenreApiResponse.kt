package com.kavrin.marvin.domain.model.movie.api


import com.kavrin.marvin.domain.model.movie.entities.MovieGenre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieGenreApiResponse(
    @SerialName("genres")
    val tvGenres: List<MovieGenre>
)