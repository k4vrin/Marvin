package com.kavrin.marvin.presentation.screens.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun HomeScreen(
	navController: NavHostController,
	homeViewModel: HomeViewModel = hiltViewModel(),
) {

	val popularMovies = homeViewModel.getPopularMovies.collectAsLazyPagingItems()
	val trendingMovies = homeViewModel.getTrendingMovie.collectAsLazyPagingItems()
	val carouselMovies = homeViewModel.getCarouselMovies.collectAsLazyPagingItems()

	Scaffold(
		topBar = {
			HomeTopBar(
				onSearchClicked = {}
			)
		}
	) {

		HomeContent(
			navHostController = navController,
			movies = carouselMovies
		)
	}

}