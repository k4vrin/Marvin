package com.kavrin.marvin.domain.use_cases.home

import androidx.paging.PagingData
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending
import kotlinx.coroutines.flow.Flow

class GetHomeTrendingTvs(
	private val repository: Repository
) {

	operator fun invoke(): Flow<PagingData<TvAndTrending>> {
		return repository.getHomeTrendingTvs()
	}
}