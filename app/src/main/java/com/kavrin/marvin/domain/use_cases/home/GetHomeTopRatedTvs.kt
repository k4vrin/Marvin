package com.kavrin.marvin.domain.use_cases.home

import androidx.paging.PagingData
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated
import kotlinx.coroutines.flow.Flow

class GetHomeTopRatedTvs(
	private val repository: Repository
) {

	operator fun invoke(): Flow<PagingData<TvAndTopRated>> {
		return repository.getHomeTopRatedTvs()
	}
}