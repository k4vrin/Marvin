package com.kavrin.marvin.domain.model.movie.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.MOVIE_GENRE_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = MOVIE_GENRE_TABLE)
@Serializable
data class MovieGenre(

    @SerialName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @SerialName("name")
    val name: String
)