package com.kavrin.marvin.domain.model.movie.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.MOVIE_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = MOVIE_TABLE)
@Serializable
data class Movie(

	@SerialName("adult")
	val adult: Boolean,

	@SerialName("backdrop_path")
	val backdropPath: String,

	@SerialName("genre_ids")
	val genreIds: List<Int>,

	@PrimaryKey(autoGenerate = false)
	@SerialName("id")
	val movieId: Int,

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

	@SerialName("release_date")
	val releaseDate: String,

	@SerialName("title")
	val title: String,

	@SerialName("video")
	val video: Boolean,

	@SerialName("vote_average")
	val voteAverage: Double,

	@SerialName("vote_count")
	val voteCount: Int,
)