package com.kavrin.marvin.domain.use_cases.home

import com.kavrin.marvin.data.repository.Repository

class DeleteAll(
    private val repository: Repository
) {

    suspend operator fun invoke() {
        repository.deleteAllMovies()
        repository.deleteAllTvs()
    }
}