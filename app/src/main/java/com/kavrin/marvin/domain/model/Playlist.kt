package com.kavrin.marvin.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.marvin.util.Constants.PLAYLIST_TABLE

@Entity(tableName = PLAYLIST_TABLE)
data class Playlist(
	@PrimaryKey(autoGenerate = true)
	val playlistId: Int,
	val playlistName: String
)
