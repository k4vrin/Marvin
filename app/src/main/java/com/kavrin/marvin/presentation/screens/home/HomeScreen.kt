package com.kavrin.marvin.presentation.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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


    val isConnected by homeViewModel.isConnected.collectAsState()

    val error by homeViewModel.error
    val loading by homeViewModel.loading

    val collapsingToolbarState by homeViewModel.collapsingToolbar

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