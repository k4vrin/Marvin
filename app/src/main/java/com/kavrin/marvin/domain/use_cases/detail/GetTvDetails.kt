package com.kavrin.marvin.domain.use_cases.detail

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.tv.api.detail.SingleTvApiResponse

class GetTvDetails(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int): SingleTvApiResponse {
        return repository.getTvDetails(id = id)
    }
}