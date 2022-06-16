package com.kavrin.marvin.domain.model.tv.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.TV_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = TV_TABLE)
@Serializable
data class Tv(

	@SerialName("backdrop_path")
	val backdropPath: String,

	@SerialName("first_air_date")
	val firstAirDate: String,

	@SerialName("genre_ids")
	val genreIds: List<Int>,

	@SerialName("id")
	@PrimaryKey(autoGenerate = false)
	val tvId: Int,

	@SerialName("name")
	val name: String,

	@SerialName("origin_country")
	val originCountry: List<String>,

	@SerialName("original_language")
	val originalLanguage: String,

	@SerialName("original_name")
	val originalName: String,

	@SerialName("overview")
	val overview: String,

	@SerialName("popularity")
	val popularity: Double,

	@SerialName("poster_path")
	val posterPath: String,

	@SerialName("vote_average")
	val voteAverage: Double,

	@SerialName("vote_count")
	val voteCount: Int,
)