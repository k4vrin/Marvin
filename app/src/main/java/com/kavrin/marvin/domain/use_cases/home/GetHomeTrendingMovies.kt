package com.kavrin.marvin.domain.use_cases.home

import androidx.paging.PagingData
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import kotlinx.coroutines.flow.Flow

class GetHomeTrendingMovies(
	private val repository: Repository
) {

	operator fun invoke(): Flow<PagingData<MovieAndTrending>> {
		return repository.getHomeTrendingMovies()
	}
}