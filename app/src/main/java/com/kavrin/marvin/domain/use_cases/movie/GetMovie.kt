package com.kavrin.marvin.domain.use_cases.movie

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.movie.entities.Movie

class GetMovie(
    private val repository: Repository
) {

    suspend operator fun invoke(movieId: Int): Movie {
        return repository.getMovie(id = movieId)
    }
}