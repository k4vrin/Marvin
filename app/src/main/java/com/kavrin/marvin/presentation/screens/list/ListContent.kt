package com.kavrin.marvin.presentation.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.kavrin.marvin.presentation.component.MovieTvItem
import com.kavrin.marvin.ui.theme.EXTRA_SMALL_PADDING
import com.kavrin.marvin.ui.theme.MAIN_CARD_WIDTH
import com.kavrin.marvin.util.MarvinMovieItem

@Composable
fun ListContent(
    items: LazyPagingItems<out MarvinMovieItem>,
    isMovie: Boolean?,
    gridState: LazyGridState,
    paddingValues: PaddingValues,
    onCardClicked: (Int) -> Unit,
    onMenuClicked: (Int) -> Unit
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

        items(
            count = items.itemCount,
        ) { index ->
            val posterPath =
                remember { if (isMovie == true) items[index]?.movie?.posterPath else items[index]?.tv?.posterPath }
            val rating =
                remember { if (isMovie == true) items[index]?.movie?.voteAverage else items[index]?.tv?.voteAverage }
            val voteCount =
                remember { if (isMovie == true) items[index]?.movie?.voteCount else items[index]?.tv?.voteCount }
            val itemId =
                remember { if (isMovie == true) items[index]?.movie?.movieId else items[index]?.tv?.tvId }
            val itemTitle =
                remember { if (isMovie == true) items[index]?.movie?.title else items[index]?.tv?.name }
            val releasedDate =
                remember { if (isMovie == true) items[index]?.movie?.releaseDate else items[index]?.tv?.firstAirDate }

            key(itemId) {
                MovieTvItem(
                    posterPath = posterPath,
                    rating = rating,
                    voteCount = voteCount,
                    itemId = itemId,
                    itemTitle = itemTitle,
                    releasedDate = releasedDate,
                    shape = RoundedCornerShape(EXTRA_SMALL_PADDING),
                    onCardClicked = { onCardClicked(it) },
                    onMenuIconClicked = { onMenuClicked(it) }
                )
            }
        }

    }

}