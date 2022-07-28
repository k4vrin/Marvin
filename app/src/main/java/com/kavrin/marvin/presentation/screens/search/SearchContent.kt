package com.kavrin.marvin.presentation.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.presentation.component.MovieTvItem
import com.kavrin.marvin.presentation.screens.search.component.ErrorStatus
import com.kavrin.marvin.ui.theme.EXTRA_SMALL_PADDING
import com.kavrin.marvin.ui.theme.MAIN_CARD_WIDTH

@Composable
fun SearchContent(
    movie: LazyPagingItems<Movie>?,
    tv: LazyPagingItems<Tv>?,
    searchType: SearchType,
    gridState: LazyGridState,
    paddingValues: PaddingValues,
    errorMessage: String,
    isErrorVisible: Boolean,
    onCardClicked: (Int) -> Unit,
    onMenuClicked: (Int) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = gridState,
            columns = GridCells.Adaptive(minSize = MAIN_CARD_WIDTH),
            contentPadding = PaddingValues(EXTRA_SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(EXTRA_SMALL_PADDING),
            horizontalArrangement = Arrangement.spacedBy(EXTRA_SMALL_PADDING)
        ) {

            when (searchType) {
                is SearchType.MovieType -> {
                    if (movie != null) {
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
                }
                is SearchType.TvType -> {
                    if (tv != null) {
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