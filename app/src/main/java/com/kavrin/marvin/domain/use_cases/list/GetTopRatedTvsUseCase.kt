package com.kavrin.marvin.domain.use_cases.list

import androidx.paging.PagingData
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated
import kotlinx.coroutines.flow.Flow

class GetTopRatedTvsUseCase(
	private val repository: Repository
) {

	operator fun invoke(): Flow<PagingData<TvAndTopRated>> {
		return repository.getAllTopRatedTvs()
	}
}