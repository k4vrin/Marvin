package com.kavrin.marvin.domain.use_cases.movie

data class MovieUseCases(
    val getMovie: GetMovie,
    val getMovieDetails: GetMovieDetails,
    val getRatings: GetRatings,
    val getCollection: GetCollection
)
