package com.kavrin.marvin.presentation.screens.home.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.presentation.component.EmptyContent
import com.kavrin.marvin.presentation.component.MovieTvItem
import com.kavrin.marvin.presentation.screens.home.search.component.ErrorStatus
import com.kavrin.marvin.ui.theme.EXTRA_SMALL_PADDING
import com.kavrin.marvin.ui.theme.MAIN_CARD_WIDTH
import com.kavrin.marvin.ui.theme.backGroundColor
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Composable
fun SearchContent(
    movie: LazyPagingItems<Movie>,
    tv: LazyPagingItems<Tv>,
    searchType: SearchType,
    gridState: LazyGridState,
    paddingValues: PaddingValues,
    errorMessage: String,
    isErrorVisible: Boolean,
    onCardClicked: (Int) -> Unit,
    onMenuClicked: (Int) -> Unit
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val errorHandler = handleSearchError(
        item = when (searchType) {
            is SearchType.MovieType -> movie
            is SearchType.TvType -> tv
            else -> tv // FIXME
        },
        isRefreshing = isRefreshing
    ) {
        isRefreshing = true
        when (searchType) {
            is SearchType.MovieType -> movie.retry()
            is SearchType.TvType -> tv.retry()
            else -> tv.retry() // FIXME
        }
        isRefreshing = false

    }

    if (errorHandler) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colors.backGroundColor),
                state = gridState,
                columns = GridCells.Adaptive(minSize = MAIN_CARD_WIDTH),
                contentPadding = PaddingValues(EXTRA_SMALL_PADDING),
                verticalArrangement = Arrangement.spacedBy(EXTRA_SMALL_PADDING),
                horizontalArrangement = Arrangement.spacedBy(EXTRA_SMALL_PADDING)
            ) {

                when (searchType) {
                    is SearchType.MovieType -> {
                        items(
                            count = movie.itemCount,
                        ) { index ->
                            val item = movie[index]
                            key(item?.movieId) {
                                MovieTvItem(
                                    posterPath = item?.posterPath,
                                    rating = item?.voteAverage,
                                    voteCount = item?.voteCount,
                                    itemId = item?.movieId,
                                    itemTitle = item?.title,
                                    releasedDate = item?.releaseDate,
                                    onCardClicked = onCardClicked,
                                    onMenuIconClicked = onMenuClicked
                                )
                            }
                        }
                    }
                    is SearchType.TvType -> {
                        items(
                            count = tv.itemCount,
                        ) { index ->
                            val item = tv[index]
                            key(item?.tvId) {
                                MovieTvItem(
                                    posterPath = item?.posterPath,
                                    rating = item?.voteAverage,
                                    voteCount = item?.voteCount,
                                    itemId = item?.tvId,
                                    itemTitle = item?.name,
                                    releasedDate = item?.firstAirDate,
                                    onCardClicked = onCardClicked,
                                    onMenuIconClicked = onMenuClicked
                                )
                            }
                        }
                    }
                    is SearchType.PersonType -> {}
                }
            }

            ErrorStatus(
                message = errorMessage,
                isVisible = isErrorVisible,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )

        }

    }



}

@Composable
private fun handleSearchError(
    item: LazyPagingItems<out Any>,
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
                    errorMessage = parseSearchErrorMessage(error = error),
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh
                )
                false
            }
            else -> true
        }
    }

}


private fun parseSearchErrorMessage(error: LoadState.Error): String {
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
        else -> error.error.message ?: "Unknown Error."
    }
}