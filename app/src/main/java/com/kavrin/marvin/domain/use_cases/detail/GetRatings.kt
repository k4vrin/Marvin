package com.kavrin.marvin.domain.use_cases.detail

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.imdb.IMDbRatingApiResponse

class GetRatings(
    private val repository: Repository
) {

    suspend operator fun invoke(id: String): IMDbRatingApiResponse {
        return repository.getRatings(id = id)
    }
}