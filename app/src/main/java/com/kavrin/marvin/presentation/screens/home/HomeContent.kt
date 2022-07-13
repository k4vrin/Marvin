package com.kavrin.marvin.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
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
import com.kavrin.marvin.presentation.screens.movie.EmptyContent
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.backGroundColor
import com.kavrin.marvin.util.MarvinItem
import com.kavrin.marvin.util.connectivityState
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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
    onRefresh: () -> Unit
) {

    val pagerState = rememberPagerState()

    var isRefreshing by remember { mutableStateOf(false) }

    val handler = handleError(
        item = carouselMovies,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            onRefresh()
            carouselMovies.refresh()
            popularMovies.refresh()
            topRatedMovies.refresh()
            trendingMovies.refresh()
            carouselTvs.refresh()
            popularTvs.refresh()
            topRatedTvs.refresh()
            trendingTvs.refresh()
            isRefreshing = false
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (handler) {
            MarvinTabRow(pagerState = pagerState)

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                onRefresh = {
                    isRefreshing = true
                    onRefresh()
                    carouselMovies.refresh()
                    popularMovies.refresh()
                    topRatedMovies.refresh()
                    trendingMovies.refresh()
                    carouselTvs.refresh()
                    popularTvs.refresh()
                    topRatedTvs.refresh()
                    trendingTvs.refresh()
                    isRefreshing = false
                }
            ) {
                HorizontalPager(
                    count = 2,
                    state = pagerState,
                    itemSpacing = 1.dp,
                    userScrollEnabled = false,
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
    val isConnected = connectivityState().collectAsState()

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
            onItemClicked = { id ->
                if (isConnected.value) {
                    navHostController.navigate(Screen.Movie.passId(id = id))
                }
            },
            onMenuIconClicked = {}
        )

        CardList(
            cardListTitle = stringResource(R.string.trending),
            items = trending,
            isMovie = true,
            onItemClicked = { id ->
                if (isConnected.value) {
                    navHostController.navigate(Screen.Movie.passId(id = id))
                }
            },
            onMenuIconClicked = {}
        )

        CardList(
            cardListTitle = stringResource(R.string.popular),
            items = popular,
            isMovie = true,
            onItemClicked = { id ->
                if (isConnected.value) {
                    navHostController.navigate(Screen.Movie.passId(id = id))
                }
            },
            onMenuIconClicked = {

            }
        )

        CardList(
            cardListTitle = stringResource(R.string.top_rated),
            items = topRated,
            isMovie = true,
            onItemClicked = { id ->
                if (isConnected.value) {
                    navHostController.navigate(Screen.Movie.passId(id = id))
                }
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
    val isConnected = connectivityState().collectAsState()

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
            onItemClicked = { id ->
                if (isConnected.value) {
                    navHostController.navigate(Screen.Tv.passId(id = id))
                }
            },
            onMenuIconClicked = {}
        )

        CardList(
            cardListTitle = stringResource(R.string.trending),
            items = trending,
            isMovie = false,
            onItemClicked = { id ->
                if (isConnected.value) {
                    navHostController.navigate(Screen.Tv.passId(id = id))
                }
            },
            onMenuIconClicked = {

            }
        )

        CardList(
            cardListTitle = stringResource(R.string.popular),
            items = popular,
            isMovie = false,
            onItemClicked = { id ->
                if (isConnected.value) {
                    navHostController.navigate(Screen.Tv.passId(id = id))
                }
            },
            onMenuIconClicked = {

            }
        )

        CardList(
            cardListTitle = stringResource(R.string.top_rated),
            items = topRated,
            isMovie = false,
            onItemClicked = { id ->
                if (isConnected.value) {
                    navHostController.navigate(Screen.Tv.passId(id = id))
                }
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


@Composable
fun <T : MarvinItem> handleError(
    item: LazyPagingItems<T>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
): Boolean {

    item.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }
        return when {
            error != null -> {
                EmptyContent(
                    isLoading = false,
                    isError = true,
                    errorMessage = parseErrorMessage(error = error),
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh
                )
                false
            }
            else -> true
        }
    }

}


fun parseErrorMessage(error: LoadState.Error): String {
    Log.d("parseErrorMessage", "error: $error")
    return when (error.error) {
        is SocketTimeoutException -> {
            "Server Unavailable."
        }
        is InterruptedIOException -> {
            "Server Unavailable."
        }
        is ConnectException -> {
            "Internet Unavailable."
        }
        is UnknownHostException -> {
            "Internet Unavailable."
        }
        else -> "Unknown Error."
    }
}