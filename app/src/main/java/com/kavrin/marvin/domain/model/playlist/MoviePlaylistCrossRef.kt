package com.kavrin.marvin.domain.model.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.kavrin.marvin.util.Constants.MOVIE_PLAYLIST_CROSS_TABLE

@Entity(tableName = MOVIE_PLAYLIST_CROSS_TABLE, primaryKeys = ["movieId", "playlistId"])
data class MoviePlaylistCrossRef(
	val movieId: Int,
	@ColumnInfo(index = true)
	val playlistId: Int
)
