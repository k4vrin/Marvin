package com.kavrin.marvin.domain.model.tv.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.TV_GENRE_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = TV_GENRE_TABLE)
@Serializable
data class TvGenre(

    @SerialName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @SerialName("name")
    val name: String
)