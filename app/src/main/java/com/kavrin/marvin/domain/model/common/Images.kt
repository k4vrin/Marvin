package com.kavrin.marvin.domain.model.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Images(
    @SerialName("backdrops")
    val backdrops: List<Backdrop>,
    @SerialName("logos")
    val logos: List<Logo>,
    @SerialName("posters")
    val posters: List<Poster>
)