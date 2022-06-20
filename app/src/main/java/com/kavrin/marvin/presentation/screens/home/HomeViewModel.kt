package com.kavrin.marvin.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.kavrin.marvin.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    useCases: UseCases
) : ViewModel() {

	val getPopularMovies = useCases.getPopularMoviesUseCase()

	val getTrendingMovie = useCases.getTrendingMoviesUseCase()

	val getCarouselMovies = useCases.carouselUseCase()
}