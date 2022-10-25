package com.kavrin.marvin.domain.model.movie.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.movie.entities.MovieTopRated
import com.kavrin.marvin.util.MarvinMovieItem

data class MovieAndTopRated(

	@Embedded
	override val movie: Movie,

	@Relation(
		parentColumn = "movieId",
		entityColumn = "topRatedMovieId",
		entity = MovieTopRated::class
	)
	val movieTopRated: MovieTopRated?
) : MarvinMovieItem
