package com.kavrin.marvin.domain.model.person


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCredits(
    @SerialName("cast")
    val personMovieCast: List<PersonMovieCast>,
    @SerialName("crew")
    val personMovieCrew: List<PersonMovieCrew>
)