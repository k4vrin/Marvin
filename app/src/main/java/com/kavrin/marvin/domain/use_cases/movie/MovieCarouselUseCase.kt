package com.kavrin.marvin.domain.use_cases.movie

import androidx.paging.PagingData
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import kotlinx.coroutines.flow.Flow

class MovieCarouselUseCase(
	private val repository: Repository
) {

	operator fun invoke(): Flow<PagingData<MovieAndTrending>> {
		return repository.getMovieCarousel()
	}
}