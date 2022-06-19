package com.kavrin.marvin.domain.use_cases

import com.kavrin.marvin.domain.use_cases.movie.GetPopularMoviesUseCase
import com.kavrin.marvin.domain.use_cases.movie.GetTopRatedMoviesUseCase
import com.kavrin.marvin.domain.use_cases.movie.GetTrendingMoviesUseCase
import com.kavrin.marvin.domain.use_cases.tv.GetPopularTvsUseCase
import com.kavrin.marvin.domain.use_cases.tv.GetTopRatedTvsUseCase
import com.kavrin.marvin.domain.use_cases.tv.GetTrendingTvsUseCase

data class UseCases(
	// Welcome Screen UseCases
	val saveOnBoardingUseCase: SaveOnBoardingUseCase,
	val readOnBoardingUseCase: ReadOnBoardingUseCase,
	val getPopularMoviesUseCase: GetPopularMoviesUseCase,
	val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
	val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
	val getPopularTvsUseCase: GetPopularTvsUseCase,
	val getTopRatedTvsUseCase: GetTopRatedTvsUseCase,
	val getTrendingTvsUseCase: GetTrendingTvsUseCase
)

