package com.kavrin.marvin.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending
import com.kavrin.marvin.presentation.common.CardList
import com.kavrin.marvin.presentation.common.Carousel
import com.kavrin.marvin.presentation.component.MarvinTabRow
import com.kavrin.marvin.presentation.component.ShimmerCardEffect
import com.kavrin.marvin.presentation.component.ShimmerCarouselEffect
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.backGroundColor
import com.kavrin.marvin.util.MarvinItem
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
	paddingValues: PaddingValues,
) {

	val pagerState = rememberPagerState()
	val scope = rememberCoroutineScope()
	val scrollState = rememberScrollState()

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(paddingValues)
			.scrollable(
				state = scrollState,
				orientation = Orientation.Vertical
			)
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

	val lazyListState = rememberLazyListState()



	LazyColumn(
		modifier = Modifier
			.fillMaxSize()
			.background(color = backGroundColor),
		verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
		state = lazyListState
	) {

		item {
			Carousel(
				items = carousel,
				isMovie = true,
				onItemClicked = {},
				onMenuIconClicked = {}
			)
		}

		item {
			CardList(
				cardListTitle = "Trending",
				items = trending,
				isMovie = true
			)
		}

		item {
			CardList(
				cardListTitle = "Popular",
				items = popular,
				isMovie = true
			)
		}

		item {
			CardList(
				cardListTitle = "Top Rated",
				items = topRated,
				isMovie = true
			)
		}
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

	val lazyListState = rememberLazyListState()


	LazyColumn(
		modifier = Modifier
			.fillMaxSize()
			.background(color = backGroundColor),
		verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
		state = lazyListState
	) {

		item {
			Carousel(
				items = carousel,
				isMovie = false,
				onItemClicked = {},
				onMenuIconClicked = {}
			)
		}

		item {
			CardList(
				cardListTitle = "Trending",
				items = trending,
				isMovie = false
			)
		}

		item {
			CardList(
				cardListTitle = "Popular",
				items = popular,
				isMovie = false
			)
		}

		item {
			CardList(
				cardListTitle = "Top Rated",
				items = topRated,
				isMovie = false
			)
		}
	}

}

@Composable
fun <T : MarvinItem> handlePagingResult(
	item: LazyPagingItems<T>,
	isCarousel: Boolean,
): Boolean {

	item.apply {

		return when {
			loadState.refresh is LoadState.Loading -> {
				if (isCarousel) {
					ShimmerCarouselEffect()
					false
				} else {
					ShimmerCardEffect()
					false
				}
			}
			else -> true
		}
	}


}