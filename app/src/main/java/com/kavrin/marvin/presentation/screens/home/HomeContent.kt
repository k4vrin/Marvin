package com.kavrin.marvin.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import com.kavrin.marvin.presentation.common.MovieCarousel
import com.kavrin.marvin.presentation.component.MarvinTabRow
import kotlinx.coroutines.launch

@Composable
fun HomeContent(
	navHostController: NavHostController,
	movies: LazyPagingItems<MovieAndTrending>,
) {

	val pagerState = rememberPagerState()
	val scope = rememberCoroutineScope()

	Column {

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
					movies = movies
				)
				else -> TvTabContent(

				)
			}
		}

	}

}


@Composable
fun MovieTabContent(
	navHostController: NavHostController,
	movies: LazyPagingItems<MovieAndTrending>,
) {

	MovieCarousel(
		trendingItems = movies,
		onItemClicked = {},
		onMenuIconClicked = {}
	)

}

@Composable
fun TvTabContent() {

}