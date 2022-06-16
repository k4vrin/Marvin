package com.kavrin.marvin.domain.model.tv.entities.relations

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.kavrin.marvin.util.Constants.TV_PLAYLIST_CROSS_TABLE

@Entity(tableName = TV_PLAYLIST_CROSS_TABLE, primaryKeys = ["tvId", "playlistId"])
data class TvPlaylistCrossRef(
	val tvId: Int,
	@ColumnInfo(index = true)
	val playlistId: Int
)
