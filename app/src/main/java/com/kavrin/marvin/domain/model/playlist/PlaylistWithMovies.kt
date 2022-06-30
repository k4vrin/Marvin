package com.kavrin.marvin.domain.model.playlist

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.kavrin.marvin.domain.model.movie.entities.Movie

data class PlaylistWithMovies(

    @Embedded
	val playlist: Playlist,

    @Relation(
		parentColumn = "playlistId",
		entityColumn = "movieId",
		associateBy = Junction(MoviePlaylistCrossRef::class)
	)
	val movie: List<Movie>,
)
