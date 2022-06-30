package com.kavrin.marvin.domain.use_cases.list

import androidx.paging.PagingData
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending
import kotlinx.coroutines.flow.Flow

class GetTrendingTvsUseCase(
	private val repository: Repository
) {

	operator fun invoke(): Flow<PagingData<TvAndTrending>> {
		return repository.getAllTrendingTvs()
	}
}