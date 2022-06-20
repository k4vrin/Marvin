package com.kavrin.marvin.presentation.screens.home

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending
import com.kavrin.marvin.presentation.common.MovieCarousel
import com.kavrin.marvin.presentation.common.TvCarousel
import com.kavrin.marvin.presentation.component.MarvinTabRow
import kotlinx.coroutines.launch

@Composable
fun HomeContent(
	navHostController: NavHostController,
	carouselMovies: LazyPagingItems<MovieAndTrending>,
	popularMovies: LazyPagingItems<MovieAndPopular>,
	topRatedMovies: LazyPagingItems<MovieAndTopRated>,
	trendingMovies: LazyPagingItems<MovieAndTrending>,
	carouselTvs: LazyPagingItems<TvAndTrending>,
	popularTvs: LazyPagingItems<TvAndPopular>,
	topRatedTvs: LazyPagingItems<TvAndTopRated>,
	trendingTvs: LazyPagingItems<TvAndTrending>,
	paddingValues: PaddingValues
) {

	val pagerState = rememberPagerState()
	val scope = rememberCoroutineScope()

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(paddingValues)
	) {

		MarvinTabRow(
			pagerState = pagerState,
			onTabClicked = { tab ->
				scope.launch {
					pagerState.animateScrollToPage(
						page = tab
					)
				}
			}
		)

		HorizontalPager(
			count = 2,
			state = pagerState,
			userScrollEnabled = false
		) {

			when (pagerState.currentPage) {
				0 -> MovieTabContent(
					navHostController = navHostController,
					carousel = carouselMovies,
					popular = popularMovies,
					topRated = topRatedMovies,
					trending = trendingMovies
				)
				else -> TvTabContent(
					navHostController = navHostController,
					carousel = carouselTvs,
					popular = popularTvs,
					topRated = topRatedTvs,
					trending = trendingTvs
				)
			}
		}

	}

}


@Composable
fun MovieTabContent(
	navHostController: NavHostController,
	carousel: LazyPagingItems<MovieAndTrending>,
	popular: LazyPagingItems<MovieAndPopular>,
	topRated: LazyPagingItems<MovieAndTopRated>,
	trending: LazyPagingItems<MovieAndTrending>,
) {

	val scrollState = rememberScrollState()

	Column(
		modifier = Modifier
			.fillMaxSize()
			.scrollable(state = scrollState, orientation = Orientation.Vertical)
	) {
		MovieCarousel(
			items = carousel,
			onItemClicked = {},
			onMenuIconClicked = {}
		)
	}
}

@Composable
fun TvTabContent(
	navHostController: NavHostController,
	carousel: LazyPagingItems<TvAndTrending>,
	popular: LazyPagingItems<TvAndPopular>,
	topRated: LazyPagingItems<TvAndTopRated>,
	trending: LazyPagingItems<TvAndTrending>,
) {

	val scrollState = rememberScrollState()

	Column(
		modifier = Modifier
			.fillMaxSize()
			.scrollable(state = scrollState, orientation = Orientation.Vertical)
	) {
		TvCarousel(
			items = carousel,
			onItemClicked = {},
			onMenuIconClicked = {}
		)
	}

}