package com.kavrin.marvin.domain.model.playlist

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.kavrin.marvin.domain.model.movie.entities.Movie

data class MovieWithPlaylists(

	@Embedded
	val movie: Movie,

	@Relation(
		parentColumn = "movieId",
		entityColumn = "playlistId",
		associateBy = Junction(MoviePlaylistCrossRef::class)
	)
	val playlist: List<Playlist>

)
