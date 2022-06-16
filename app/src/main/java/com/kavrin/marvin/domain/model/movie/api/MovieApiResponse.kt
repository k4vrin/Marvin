package com.kavrin.marvin.domain.model.movie.api


import com.kavrin.marvin.domain.model.movie.entities.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieApiResponse(

	@SerialName("page")
    val page: Int,

	@SerialName("results")
    val movies: List<Movie>,

	@SerialName("total_pages")
    val totalPages: Int,

	@SerialName("total_results")
    val totalResults: Int
)