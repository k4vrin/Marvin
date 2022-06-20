package com.kavrin.marvin.domain.use_cases.home

import androidx.paging.PagingData
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import kotlinx.coroutines.flow.Flow

class GetHomePopularMovies(
	private val repository: Repository
) {

	operator fun invoke(): Flow<PagingData<MovieAndPopular>> {
		return repository.getHomePopularMovies()
	}
}