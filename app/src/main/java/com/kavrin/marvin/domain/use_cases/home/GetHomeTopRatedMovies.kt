package com.kavrin.marvin.domain.use_cases.home

import androidx.paging.PagingData
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import kotlinx.coroutines.flow.Flow

class GetHomeTopRatedMovies(
	private val repository: Repository
) {

	operator fun invoke(): Flow<PagingData<MovieAndTopRated>> {
		return repository.getHomeTopRatedMovies()
	}
}