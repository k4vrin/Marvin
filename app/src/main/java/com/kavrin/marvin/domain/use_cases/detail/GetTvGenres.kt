package com.kavrin.marvin.domain.use_cases.detail

import com.kavrin.marvin.data.repository.Repository

class GetTvGenres(
    private val repository: Repository
) {

    suspend operator fun invoke(ids: List<Int>): List<String> {
        return repository.getTvGenres(ids)
    }
}