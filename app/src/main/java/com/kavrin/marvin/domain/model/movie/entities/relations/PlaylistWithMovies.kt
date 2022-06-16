package com.kavrin.marvin.domain.model.movie.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.Playlist

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
