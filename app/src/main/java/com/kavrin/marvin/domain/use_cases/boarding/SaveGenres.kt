package com.kavrin.marvin.domain.use_cases.boarding

import com.kavrin.marvin.data.repository.Repository

class SaveGenres(
    private val repository: Repository
) {

    suspend operator fun invoke() {
        repository.saveGenres()
    }
}