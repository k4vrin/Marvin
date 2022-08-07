package com.kavrin.marvin.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.kavrin.marvin.navigation.util.Graph
import com.kavrin.marvin.navigation.util.HomeScreens
import com.kavrin.marvin.presentation.component.EmptyContent
import com.kavrin.marvin.presentation.screens.home.component.CardList
import com.kavrin.marvin.presentation.screens.home.component.Carousel
import com.kavrin.marvin.presentation.screens.home.component.MarvinTabRow
import com.kavrin.marvin.ui.theme.EXTRA_LARGE_PADDING
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.backGroundColor
import com.kavrin.marvin.util.Constants
import com.kavrin.marvin.util.MarvinItem
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
    isConnected: Boolean,
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

        MarvinTabRow(pagerState = pagerState)
        if (handler) {

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
                        trending = trendingMovies,
                        isConnected = isConnected
                    )
                    1 -> TvTabContent(
                        navHostController = navHostController,
                        carousel = carouselTvs,
                        popular = popularTvs,
                        topRated = topRatedTvs,
                        trending = trendingTvs,
                        isConnected = isConnected
                    )
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
    isConnected: Boolean
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.backGroundColor)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
    ) {


        Carousel(
            items = carousel,
            isMovie = true,
            onItemClicked = { id ->
                if (isConnected) {
                    navHostController.navigate(Graph.Movie.passId(id = id))
                }
            },
            onMenuIconClicked = { /*TODO*/ }
        )

        CardList(
            cardListTitle = Constants.TRENDING,
            items = trending,
            isMovie = true,
            onItemClicked = { id ->
                if (isConnected) {
                    navHostController.navigate(Graph.Movie.passId(id = id))
                }
            },
            onSeeAllClicked = {
                navHostController.navigate(
                    HomeScreens.List.passListName(
                        listName = Constants.TRENDING,
                        isMovie = true
                    )
                )
            },
            onMenuIconClicked = { /*TODO*/ }
        )

        CardList(
            cardListTitle = Constants.POPULAR,
            items = popular,
            isMovie = true,
            onItemClicked = { id ->
                if (isConnected) {
                    navHostController.navigate(Graph.Movie.passId(id = id))
                }
            },
            onSeeAllClicked = {
                navHostController.navigate(
                    HomeScreens.List.passListName(
                        listName = Constants.POPULAR,
                        isMovie = true
                    )
                )
            },
            onMenuIconClicked = { /*TODO*/ }
        )

        CardList(
            cardListTitle = Constants.TOP_RATED,
            items = topRated,
            isMovie = true,
            onItemClicked = { id ->
                if (isConnected) {
                    navHostController.navigate(Graph.Movie.passId(id = id))
                }
            },
            onSeeAllClicked = {
                navHostController.navigate(
                    HomeScreens.List.passListName(
                        listName = Constants.TOP_RATED,
                        isMovie = true
                    )
                )
            },
            onMenuIconClicked = { /*TODO*/ }
        )

        Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
    }

}

@Composable
fun TvTabContent(
    navHostController: NavHostController,
    carousel: LazyPagingItems<TvAndTrending>,
    popular: LazyPagingItems<TvAndPopular>,
    topRated: LazyPagingItems<TvAndTopRated>,
    trending: LazyPagingItems<TvAndTrending>,
    isConnected: Boolean
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.backGroundColor)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
    ) {

        Carousel(
            items = carousel,
            isMovie = false,
            onItemClicked = { id ->
                if (isConnected) {
                    navHostController.navigate(Graph.Tv.passId(id = id))
                }
            },
            onMenuIconClicked = { /*TODO*/ }
        )

        CardList(
            cardListTitle = Constants.TRENDING,
            items = trending,
            isMovie = false,
            onItemClicked = { id ->
                if (isConnected) {
                    navHostController.navigate(Graph.Tv.passId(id = id))
                }
            },
            onSeeAllClicked = {
                navHostController.navigate(
                    HomeScreens.List.passListName(
                        listName = Constants.TRENDING,
                        isMovie = false
                    )
                )
            },
            onMenuIconClicked = { /*TODO*/ }
        )

        CardList(
            cardListTitle = Constants.POPULAR,
            items = popular,
            isMovie = false,
            onItemClicked = { id ->
                if (isConnected) {
                    navHostController.navigate(Graph.Tv.passId(id = id))
                }
            },
            onSeeAllClicked = {
                navHostController.navigate(
                    HomeScreens.List.passListName(
                        listName = Constants.POPULAR,
                        isMovie = false
                    )
                )
            },
            onMenuIconClicked = { /*TODO*/ }
        )

        CardList(
            cardListTitle = Constants.TOP_RATED,
            items = topRated,
            isMovie = false,
            onItemClicked = { id ->
                if (isConnected) {
                    navHostController.navigate(Graph.Tv.passId(id = id))
                }
            },
            onSeeAllClicked = {
                navHostController.navigate(
                    HomeScreens.List.passListName(
                        listName = Constants.TOP_RATED,
                        isMovie = false
                    )
                )
            },
            onMenuIconClicked = { /*TODO*/ }
        )

        Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))

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
                    isError = true,
                    errorMessage = parseErrorMessage(error = error),
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh
                )
                false
            }
            loadState.refresh is LoadState.Loading || itemCount < 4 -> {
                EmptyContent(isError = false)
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