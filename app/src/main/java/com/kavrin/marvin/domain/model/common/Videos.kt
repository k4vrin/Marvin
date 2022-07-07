package com.kavrin.marvin.domain.model.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Videos(
    @SerialName("results")
    val videos: List<Video>
)