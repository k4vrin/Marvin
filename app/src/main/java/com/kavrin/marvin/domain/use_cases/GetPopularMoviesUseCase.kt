package com.kavrin.marvin.domain.use_cases

import androidx.paging.PagingData
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(
	private val repository: Repository
) {

	operator fun invoke(): Flow<PagingData<MovieAndPopular>> {
		return repository.getAllPopularMovies()
	}
}