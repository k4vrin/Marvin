package com.kavrin.marvin.domain.model.movie.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.movie.entities.MovieTrending

data class MovieAndTrending(

	@Embedded
	val movie: Movie?,

	@Relation(
		parentColumn = "movieId",
		entityColumn = "trendingMovieId",
		entity = MovieTrending::class
	)
	val movieTrending: MovieTrending?
)
