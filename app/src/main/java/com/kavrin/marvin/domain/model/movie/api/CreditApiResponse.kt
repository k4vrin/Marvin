package com.kavrin.marvin.domain.model.movie.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditApiResponse(
    @SerialName("cast")
    val cast: List<Cast>,
    @SerialName("crew")
    val crew: List<Crew>,
    @SerialName("id")
    val id: Int
)