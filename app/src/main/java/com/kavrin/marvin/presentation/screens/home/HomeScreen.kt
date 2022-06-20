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

	val carouselMovies = homeViewModel.getCarouselMovies.collectAsLazyPagingItems()
	val popularMovies = homeViewModel.getPopularMovies.collectAsLazyPagingItems()
	val topRatedMovies = homeViewModel.getTopRatedMovies.collectAsLazyPagingItems()
	val trendingMovies = homeViewModel.getTrendingMovies.collectAsLazyPagingItems()

	val carouselTvs = homeViewModel.getCarouselTvs.collectAsLazyPagingItems()
	val popularTvs = homeViewModel.getPopularTvs.collectAsLazyPagingItems()
	val topRatedTvs = homeViewModel.getTopRatedTvs.collectAsLazyPagingItems()
	val trendingTvs = homeViewModel.getTrendingTvs.collectAsLazyPagingItems()

	Scaffold(
		topBar = {
			HomeTopBar(
				onSearchClicked = {}
			)
		}
	) {

		HomeContent(
			navHostController = navController,
			carouselMovies = carouselMovies,
			popularMovies = popularMovies,
			topRatedMovies = topRatedMovies,
			trendingMovies = trendingMovies,
			carouselTvs = carouselTvs,
			popularTvs = popularTvs,
			topRatedTvs = topRatedTvs,
			trendingTvs = trendingTvs,
			paddingValues = it
		)
	}

}