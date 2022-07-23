package com.kavrin.marvin.domain.model.movie.api.detail


import com.kavrin.marvin.domain.model.common.*
import com.kavrin.marvin.domain.model.movie.api.MovieApiResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SingleMovieApiResponse(
    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection?,
    @SerialName("credits")
    val credits: Credits,
    @SerialName("genres")
    val genres: List<Genre>,
    @SerialName("homepage")
    val homepage: String,
    @SerialName("id")
    val id: Int,
    @SerialName("images")
    val images: Images,
    @SerialName("imdb_id")
    val imdbId: String?,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("runtime")
    val runtime: Int,
    @SerialName("tagline")
    val tagline: String,
    @SerialName("videos")
    val videos: Videos,
    @SerialName("reviews")
    val reviews: Reviews?,
    @SerialName("recommendations")
    val recommendations: MovieApiResponse,
    @SerialName("similar")
    val similar: MovieApiResponse
)