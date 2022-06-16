package com.kavrin.marvin.domain.model.tv.api


import com.kavrin.marvin.domain.model.tv.entities.TvGenre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvGenreApiResponse(

    @SerialName("genres")
    val tvGenres: List<TvGenre>
)