package com.kavrin.marvin.domain.use_cases.search

import androidx.paging.PagingData
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.movie.entities.Movie
import kotlinx.coroutines.flow.Flow

class SearchMovies(
    private val repository: Repository
) {

    @kotlin.jvm.Throws(InvalidQueryException::class)
    operator fun invoke(query: String): Flow<PagingData<Movie>> {
        when {
            query.isBlank() -> throw InvalidQueryException(message = "The search query can't be empty.")
            else -> return repository.searchMovies(query = query)
        }
    }

}