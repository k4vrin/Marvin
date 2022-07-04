package com.kavrin.marvin.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kavrin.marvin.R
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending
import com.kavrin.marvin.navigation.Screen
import com.kavrin.marvin.presentation.common.CardList
import com.kavrin.marvin.presentation.common.Carousel
import com.kavrin.marvin.presentation.component.MarvinTabRow
import com.kavrin.marvin.presentation.component.ShimmerCardEffect
import com.kavrin.marvin.presentation.component.ShimmerCarouselEffect
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.backGroundColor
import com.kavrin.marvin.util.MarvinItem

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        MarvinTabRow(pagerState = pagerState)

        HorizontalPager(
            count = 2,
            state = pagerState,
            userScrollEnabled = false
        ) { page ->

            when (page) {
                0 -> MovieTabContent(
                    navHostController = navHostController,
                    carousel = carouselMovies,
                    popular = popularMovies,
                    topRated = topRatedMovies,
                    trending = trendingMovies
                )
                1 -> TvTabContent(
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
            .background(color = MaterialTheme.colors.backGroundColor)
            .verticalScroll(
                state = scrollState
            ),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
    ) {

        Carousel(
            items = carousel,
            isMovie = true,
            onItemClicked = { id, isMovie ->
                navHostController.navigate(
                    Screen.Detail.passIdAndBool(
                        id = id,
                        isMovie = isMovie
                    )
                )
            },
            onMenuIconClicked = {}
        )

        CardList(
            cardListTitle = stringResource(R.string.trending),
            items = trending,
            isMovie = true,
            onItemClicked = { id, isMovie ->
                navHostController.navigate(
                    Screen.Detail.passIdAndBool(
                        id = id,
                        isMovie = isMovie
                    )
                )
            },
            onMenuIconClicked = {}
        )

        CardList(
            cardListTitle = stringResource(R.string.popular),
            items = popular,
            isMovie = true,
            onItemClicked = { id, isMovie ->
                navHostController.navigate(
                    Screen.Detail.passIdAndBool(
                        id = id,
                        isMovie = isMovie
                    )
                )
            },
            onMenuIconClicked = {

            }
        )

        CardList(
            cardListTitle = stringResource(R.string.top_rated),
            items = topRated,
            isMovie = true,
            onItemClicked = { id, isMovie ->
                navHostController.navigate(
                    Screen.Detail.passIdAndBool(
                        id = id,
                        isMovie = isMovie
                    )
                )
            },
            onMenuIconClicked = {

            }
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
            .background(color = MaterialTheme.colors.backGroundColor)
            .verticalScroll(
                state = scrollState,
            ),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
    ) {

        Carousel(
            items = carousel,
            isMovie = false,
            onItemClicked = { id, isMovie ->
                navHostController.navigate(
                    Screen.Detail.passIdAndBool(
                        id = id,
                        isMovie = isMovie
                    )
                )
            },
            onMenuIconClicked = {}
        )

        CardList(
            cardListTitle = stringResource(R.string.trending),
            items = trending,
            isMovie = false,
            onItemClicked = { id, isMovie ->
                navHostController.navigate(
                    Screen.Detail.passIdAndBool(
                        id = id,
                        isMovie = isMovie
                    )
                )
            },
            onMenuIconClicked = {

            }
        )

        CardList(
            cardListTitle = stringResource(R.string.popular),
            items = popular,
            isMovie = false,
            onItemClicked = { id, isMovie ->
                navHostController.navigate(
                    Screen.Detail.passIdAndBool(
                        id = id,
                        isMovie = isMovie
                    )
                )
            },
            onMenuIconClicked = {

            }
        )

        CardList(
            cardListTitle = stringResource(R.string.top_rated),
            items = topRated,
            isMovie = false,
            onItemClicked = { id, isMovie ->
                navHostController.navigate(
                    Screen.Detail.passIdAndBool(
                        id = id,
                        isMovie = isMovie
                    )
                )
            },
            onMenuIconClicked = {

            }
        )
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
            item.itemCount < 3 -> {
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