package com.kavrin.marvin.domain.use_cases.tv

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.tv.entities.Tv

class GetTv(
    private val repository: Repository
) {

    suspend operator fun invoke(tvId: Int): Tv {
        return repository.getTv(id = tvId)
    }
}