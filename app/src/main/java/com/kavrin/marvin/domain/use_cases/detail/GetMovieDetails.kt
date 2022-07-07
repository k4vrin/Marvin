package com.kavrin.marvin.domain.use_cases.detail

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.movie.api.detail.SingleMovieApiResponse

class GetMovieDetails(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int): SingleMovieApiResponse {
        return repository.getMovieDetails(id = id)
    }
}