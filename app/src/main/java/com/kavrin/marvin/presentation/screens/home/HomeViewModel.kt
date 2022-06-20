package com.kavrin.marvin.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.kavrin.marvin.domain.use_cases.home.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    useCases: HomeUseCases
) : ViewModel() {


	val getCarouselMovies = useCases.getCarouselMovies()
	val getPopularMovies = useCases.getHomePopularMovies()
	val getTopRatedMovies = useCases.getHomeTopRatedMovies()
	val getTrendingMovies = useCases.getHomeTrendingMovies()

	val getCarouselTvs = useCases.getCarouselTvs()
	val getPopularTvs = useCases.getHomePopularTvs()
	val getTopRatedTvs = useCases.getHomeTopRatedTvs()
	val getTrendingTvs = useCases.getHomeTrendingTvs()
}