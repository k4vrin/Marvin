package com.kavrin.marvin.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import com.kavrin.marvin.presentation.common.MovieCarousel
import com.kavrin.marvin.ui.theme.BrightMaroon
import com.kavrin.marvin.ui.theme.fonts
import com.kavrin.marvin.ui.theme.topBarBgColor
import com.kavrin.marvin.ui.theme.topBarContentColor
import kotlinx.coroutines.launch

@Composable
fun HomeContent(
	navHostController: NavHostController,
	movies: LazyPagingItems<MovieAndTrending>,
) {

	val pagerState = rememberPagerState()
	val scope = rememberCoroutineScope()

	Column {

		HomeTabRow(
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
		items = movies,
		onItemClicked = {},
		onMenuIconClicked = {}
	)

}

@Composable
fun TvTabContent() {

}

@Composable
fun HomeTabRow(
	pagerState: PagerState,
	onTabClicked: (Int) -> Unit,
) {

	val titles = listOf("Movie", "Series")
	val indicator = @Composable { tabPositions: List<TabPosition> ->
		TabIndicator(
			color = BrightMaroon,
			modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
		)
	}


	TabRow(
		selectedTabIndex = pagerState.currentPage,
		backgroundColor = MaterialTheme.colors.topBarBgColor,
		contentColor = MaterialTheme.colors.topBarContentColor,
		indicator = indicator
	) {
		titles.forEachIndexed { index, title ->
			Tab(
				selected = pagerState.currentPage == index,
				onClick = { onTabClicked(index) },
				text = {
					Text(
						text = title,
						fontFamily = fonts,
						fontSize = MaterialTheme.typography.h6.fontSize,
						fontWeight = FontWeight.Bold
					)
				},
			)
		}

	}
}

@Composable
fun TabIndicator(
	color: Color,
	modifier: Modifier = Modifier,
) {
	Spacer(
		modifier
			.padding(horizontal = 24.dp)
			.height(4.dp)
			.background(
				color = color,
				shape = RoundedCornerShape(
					topStartPercent = 100, topEndPercent = 100
				)
			)
	)
}

@Preview
@Composable
fun HomeTabRowPreview() {

	HomeTabRow(
		pagerState = rememberPagerState()
	) {}
}