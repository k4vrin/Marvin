package com.kavrin.marvin.presentation.screens.home

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kavrin.marvin.ui.theme.statusBarColor
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

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

	val ui = rememberSystemUiController()
	val color = MaterialTheme.colors.statusBarColor
	val useDarkIcons = MaterialTheme.colors.isLight
	SideEffect {
		ui.setStatusBarColor(color = color, darkIcons = useDarkIcons)
	}

	CollapsingToolbarScaffold(
		modifier = Modifier
			.systemBarsPadding(),
		state = rememberCollapsingToolbarScaffoldState(),
		scrollStrategy = ScrollStrategy.EnterAlways,
		toolbar = {
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
		)
	}

}