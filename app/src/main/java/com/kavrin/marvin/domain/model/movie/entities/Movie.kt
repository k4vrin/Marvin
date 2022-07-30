package com.kavrin.marvin.domain.model.movie.entities


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.MOVIE_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = MOVIE_TABLE, indices = [Index(value = ["movieId"], unique = true),])
@Serializable
data class Movie(

	@PrimaryKey(autoGenerate = true)
	val localId: Int = 0,

	@SerialName("adult")
	val adult: Boolean,

	@SerialName("backdrop_path")
	val backdropPath: String?,

	@SerialName("genre_ids")
	val genreIds: List<Int>,

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
	val posterPath: String?,

	@SerialName("release_date")
	val releaseDate: String? = null,

	@SerialName("title")
	val title: String,

	@SerialName("video")
	val video: Boolean,

	@SerialName("vote_average")
	val voteAverage: Double,

	@SerialName("vote_count")
	val voteCount: Int,
)