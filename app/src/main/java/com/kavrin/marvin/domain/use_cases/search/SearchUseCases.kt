package com.kavrin.marvin.domain.use_cases.search

data class SearchUseCases(
    val searchMovies: SearchMovies
)



class InvalidQueryException(message: String) : Exception()
