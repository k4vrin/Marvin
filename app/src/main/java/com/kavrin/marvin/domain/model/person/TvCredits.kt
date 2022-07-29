package com.kavrin.marvin.domain.model.person


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvCredits(
    @SerialName("cast")
    val personTvCast: List<PersonTvCast>,
    @SerialName("crew")
    val personTvCrew: List<PersonTvCrew>
)