package com.kavrin.marvin.presentation.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kavrin.marvin.navigation.util.Durations
import com.kavrin.marvin.navigation.util.HomeScreens
import com.kavrin.marvin.ui.theme.statusBarColor
import kotlinx.coroutines.delay
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy

@Composable
fun HomeScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {

    val ui = rememberSystemUiController()
    val color = MaterialTheme.colors.statusBarColor
    val useDarkIcons = MaterialTheme.colors.isLight
    LaunchedEffect(key1 = true) {
        delay(timeMillis = Durations.MEDIUM.toLong())
        ui.setStatusBarColor(color = color, darkIcons = useDarkIcons)
    }

    val carouselMovies = homeViewModel.getCarouselMovies.collectAsLazyPagingItems()
    val popularMovies = homeViewModel.getPopularMovies.collectAsLazyPagingItems()
    val topRatedMovies = homeViewModel.getTopRatedMovies.collectAsLazyPagingItems()
    val trendingMovies = homeViewModel.getTrendingMovies.collectAsLazyPagingItems()

    val carouselTvs = homeViewModel.getCarouselTvs.collectAsLazyPagingItems()
    val popularTvs = homeViewModel.getPopularTvs.collectAsLazyPagingItems()
    val topRatedTvs = homeViewModel.getTopRatedTvs.collectAsLazyPagingItems()
    val trendingTvs = homeViewModel.getTrendingTvs.collectAsLazyPagingItems()

    val popularTvsLazyListState = rememberLazyListState()
    val topRatedTvsListState = rememberLazyListState()
    val trendingTvsLazyListState = rememberLazyListState()
    val popularMoviesLazyListState = rememberLazyListState()
    val topRatedMoviesListState = rememberLazyListState()
    val trendingMoviesLazyListState = rememberLazyListState()
    val movieScrollState = rememberScrollState()
    val tvScrollState = rememberScrollState()
    val pagerState = rememberPagerState()

    val scrollState by homeViewModel.scrollState

    val isConnected by homeViewModel.isConnected.collectAsState()

    val error by homeViewModel.error
    val loading by homeViewModel.loading

    val collapsingToolbarState by homeViewModel.collapsingToolbar

    LaunchedEffect(key1 = true) {
        handleScrollTo(
            popularTvsLazyListState = popularTvsLazyListState,
            topRatedTvsListState = topRatedTvsListState,
            trendingTvsLazyListState = trendingTvsLazyListState,
            popularMoviesLazyListState = popularMoviesLazyListState,
            topRatedMoviesListState = topRatedMoviesListState,
            trendingMoviesLazyListState = trendingMoviesLazyListState,
            scrollState = scrollState
        )
    }

    HandleScroll(
        popularTvsLazyListState = popularTvsLazyListState,
        topRatedTvsListState = topRatedTvsListState,
        trendingTvsLazyListState = trendingTvsLazyListState,
        popularMoviesLazyListState = popularMoviesLazyListState,
        topRatedMoviesListState = topRatedMoviesListState,
        trendingMoviesLazyListState = trendingMoviesLazyListState,
        homeViewModel = homeViewModel
    )

    HandleError(
        item = carouselMovies,
        onError = {
            homeViewModel.updateError(
                isError = true,
                message = it
            )
            homeViewModel.updateLoading(
                isLoading = false
            )
        },
        onLoading = {
            homeViewModel.updateError(
                isError = false
            )
            homeViewModel.updateLoading(
                isLoading = it
            )
        }
    )

    CollapsingToolbarScaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .statusBarsPadding(),
        state = collapsingToolbarState,
        scrollStrategy = ScrollStrategy.EnterAlways,
        toolbar = {
            HomeTopBar(
                isConnected = isConnected,
                onSearchClicked = { navController.navigate(HomeScreens.Search.route) }
            )
        },
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
            isConnected = isConnected,
            popularTvsLazyListState = popularTvsLazyListState,
            topRatedTvsListState = topRatedTvsListState,
            trendingTvsLazyListState = trendingTvsLazyListState,
            popularMoviesLazyListState = popularMoviesLazyListState,
            topRatedMoviesListState = topRatedMoviesListState,
            trendingMoviesLazyListState = trendingMoviesLazyListState,
            movieScrollState = movieScrollState,
            tvScrollState = tvScrollState,
            screenPagerState = pagerState,
            error = error,
            loading = loading,
            onRefresh = { homeViewModel.deleteAll() }
        )
    }
}

@Composable
fun HandleScroll(
    popularTvsLazyListState: LazyListState,
    topRatedTvsListState: LazyListState,
    trendingTvsLazyListState: LazyListState,
    popularMoviesLazyListState: LazyListState,
    topRatedMoviesListState: LazyListState,
    trendingMoviesLazyListState: LazyListState,
    homeViewModel: HomeViewModel
) {

    if (popularMoviesLazyListState.isScrollInProgress) {
        DisposableEffect(key1 = true) {
            onDispose {
                homeViewModel.updateScrollState(
                    scrollState = homeViewModel.scrollState.value.copy(
                        popularMovie = ScrollDetail(
                            index = popularMoviesLazyListState.firstVisibleItemIndex,
                            offset = popularMoviesLazyListState.firstVisibleItemScrollOffset
                        )
                    )
                )
            }
        }
    }

    if (topRatedMoviesListState.isScrollInProgress) {
        DisposableEffect(key1 = true) {
            onDispose {
                homeViewModel.updateScrollState(
                    scrollState = homeViewModel.scrollState.value.copy(
                        topRatedMovie = ScrollDetail(
                            index = topRatedMoviesListState.firstVisibleItemIndex,
                            offset = topRatedMoviesListState.firstVisibleItemScrollOffset
                        )
                    )
                )
            }
        }
    }

    if (trendingMoviesLazyListState.isScrollInProgress) {
        DisposableEffect(key1 = true) {
            onDispose {
                homeViewModel.updateScrollState(
                    scrollState = homeViewModel.scrollState.value.copy(
                        trendingMovie = ScrollDetail(
                            index = trendingMoviesLazyListState.firstVisibleItemIndex,
                            offset = trendingMoviesLazyListState.firstVisibleItemScrollOffset
                        )
                    )
                )
            }
        }
    }

    if (popularTvsLazyListState.isScrollInProgress) {
        DisposableEffect(key1 = true) {
            onDispose {
                homeViewModel.updateScrollState(
                    scrollState = homeViewModel.scrollState.value.copy(
                        popularTv = ScrollDetail(
                            index = popularTvsLazyListState.firstVisibleItemIndex,
                            offset = popularTvsLazyListState.firstVisibleItemScrollOffset
                        )
                    )
                )
            }
        }
    }

    if (topRatedTvsListState.isScrollInProgress) {
        DisposableEffect(key1 = true) {
            onDispose {
                homeViewModel.updateScrollState(
                    scrollState = homeViewModel.scrollState.value.copy(
                        topRatedTv = ScrollDetail(
                            index = topRatedTvsListState.firstVisibleItemIndex,
                            offset = topRatedTvsListState.firstVisibleItemScrollOffset
                        )
                    )
                )
            }
        }
    }

    if (trendingTvsLazyListState.isScrollInProgress) {
        DisposableEffect(key1 = true) {
            onDispose {
                homeViewModel.updateScrollState(
                    scrollState = homeViewModel.scrollState.value.copy(
                        trendingTv = ScrollDetail(
                            index = trendingTvsLazyListState.firstVisibleItemIndex,
                            offset = trendingTvsLazyListState.firstVisibleItemScrollOffset
                        )
                    )
                )
            }
        }
    }

}

suspend fun handleScrollTo(
    popularTvsLazyListState: LazyListState,
    topRatedTvsListState: LazyListState,
    trendingTvsLazyListState: LazyListState,
    popularMoviesLazyListState: LazyListState,
    topRatedMoviesListState: LazyListState,
    trendingMoviesLazyListState: LazyListState,
    scrollState: ScrollState
) {

    delay(Durations.SHORT.toLong())

    popularMoviesLazyListState.scrollToItem(
        index = scrollState.popularMovie.index,
        scrollOffset = scrollState.popularMovie.offset
    )

    topRatedMoviesListState.scrollToItem(
        index = scrollState.topRatedMovie.index,
        scrollOffset = scrollState.topRatedMovie.offset
    )

    trendingMoviesLazyListState.scrollToItem(
        index = scrollState.trendingMovie.index,
        scrollOffset = scrollState.trendingMovie.offset
    )

    popularTvsLazyListState.scrollToItem(
        index = scrollState.popularTv.index,
        scrollOffset = scrollState.popularTv.offset
    )

    topRatedTvsListState.scrollToItem(
        index = scrollState.topRatedTv.index,
        scrollOffset = scrollState.topRatedTv.offset
    )

    trendingTvsLazyListState.scrollToItem(
        index = scrollState.trendingTv.index,
        scrollOffset = scrollState.trendingTv.offset
    )

}