package com.kavrin.marvin.domain.use_cases.detail

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.movie.api.Cast

class GetCast(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int, isMovie: Boolean): List<Cast> {
        return if (isMovie)
            repository.getMovieCredits(id = id).cast
        else
            repository.getTvCredits(id = id).cast
    }
}