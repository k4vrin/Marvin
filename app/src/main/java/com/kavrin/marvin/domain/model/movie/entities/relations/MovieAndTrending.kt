package com.kavrin.marvin.domain.model.movie.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.movie.entities.MovieTrending
import com.kavrin.marvin.util.MarvinMovieItem

data class MovieAndTrending(

	@Embedded
	override val movie: Movie,

	@Relation(
		parentColumn = "movieId",
		entityColumn = "trendingMovieId",
		entity = MovieTrending::class
	)
	val movieTrending: MovieTrending?
) : MarvinMovieItem
