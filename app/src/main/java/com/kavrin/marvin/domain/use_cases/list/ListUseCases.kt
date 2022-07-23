package com.kavrin.marvin.domain.use_cases.list

data class ListUseCases(
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    val getPopularTvsUseCase: GetPopularTvsUseCase,
    val getTopRatedTvsUseCase: GetTopRatedTvsUseCase,
    val getTrendingTvsUseCase: GetTrendingTvsUseCase
)