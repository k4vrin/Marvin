package com.kavrin.marvin.domain.use_cases.tv

import androidx.paging.PagingData
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular
import kotlinx.coroutines.flow.Flow

class GetPopularTvsUseCase(
	private val repository: Repository
) {

	operator fun invoke(): Flow<PagingData<TvAndPopular>> {
		return repository.getAllPopularTvs()
	}
}