package com.kavrin.marvin.domain.use_cases.movie

import com.kavrin.marvin.domain.model.common.*
import com.kavrin.marvin.domain.model.movie.entities.Movie

data class UiMovie(
    val imdbId: String?,
    val toolbarInfo: Map<String, String?>,
    val releaseRuntimeStatus: Map<String, String?>,
    val overview: String?,
    val genres: List<String>?,
    val trailer: Video?,
    val trailerBackdrop: Backdrop?,
    val videos: List<Video>?,
    val reviews: List<Review>?,
    val collectionId: Int?,
    val cast: List<Cast>?,
    val crew: List<Crew>?,
    val recommendations: List<Movie>?,
    val similars: List<Movie>?,
)