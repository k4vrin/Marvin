package com.kavrin.marvin.domain.model.playlist

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.kavrin.marvin.domain.model.tv.entities.Tv

data class PlaylistWithTvs(

    @Embedded
	val playlist: Playlist,

    @Relation(
		parentColumn = "playlistId",
		entityColumn = "tvId",
		associateBy = Junction(TvPlaylistCrossRef::class)
	)
	val movie: List<Tv>,
)
