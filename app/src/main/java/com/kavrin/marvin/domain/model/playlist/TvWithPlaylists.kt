package com.kavrin.marvin.domain.model.playlist

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.kavrin.marvin.domain.model.tv.entities.Tv

data class TvWithPlaylists(

	@Embedded
	val tv: Tv,

	@Relation(
		parentColumn = "tvId",
		entityColumn = "playlistId",
		associateBy = Junction(TvPlaylistCrossRef::class)
	)
	val playlist: List<Playlist>
)
