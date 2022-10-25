package com.kavrin.marvin.domain.model.movie.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.movie.entities.MoviePopular
import com.kavrin.marvin.util.MarvinMovieItem

data class MovieAndPopular(

	@Embedded
	override val movie: Movie,

	@Relation(
		parentColumn = "movieId",
		entityColumn = "popularMovieId",
		entity = MoviePopular::class
	)
	val moviePopular: MoviePopular?,
) : MarvinMovieItem
