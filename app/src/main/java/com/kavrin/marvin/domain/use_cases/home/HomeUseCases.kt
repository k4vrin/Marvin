package com.kavrin.marvin.domain.use_cases.home

data class HomeUseCases(
	val getCarouselMovies: GetCarouselMovies,
	val getHomePopularMovies: GetHomePopularMovies,
	val getHomeTopRatedMovies: GetHomeTopRatedMovies,
	val getHomeTrendingMovies: GetHomeTrendingMovies,
	val getCarouselTvs: GetCarouselTvs,
	val getHomePopularTvs: GetHomePopularTvs,
	val getHomeTopRatedTvs: GetHomeTopRatedTvs,
	val getHomeTrendingTvs: GetHomeTrendingTvs
)

