package com.kavrin.marvin.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.kavrin.marvin.R
import com.kavrin.marvin.presentation.component.MovieTvItem
import com.kavrin.marvin.presentation.screens.home.handlePagingResult
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.contentColor
import com.kavrin.marvin.ui.theme.fonts
import com.kavrin.marvin.util.MarvinItem

@Composable
fun <T : MarvinItem> CardList(
    cardListTitle: String,
    items: LazyPagingItems<T>,
    isMovie: Boolean,
    onItemClicked: (Int, Boolean) -> Unit,
    onMenuIconClicked: (Int) -> Unit
) {

    val listState = rememberLazyListState()
    val result = handlePagingResult(item = items, isCarousel = false)

    if (result) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MEDIUM_PADDING),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = cardListTitle,
                    fontFamily = fonts,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.contentColor
                )

                Text(
                    text = stringResource(R.string.see_all),
                    fontFamily = fonts,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colors.contentColor
                )
            }

            LazyRow(
                state = listState,
                contentPadding = PaddingValues(all = MEDIUM_PADDING),
                horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
            ) {

                items(
                    items = items,
                    key = {
                        if (isMovie)
                            it.movie?.movieId!!
                        else
                            it.tv?.tvId!!
                    }
                ) { marvinItem ->

                    val posterPath =
                        if (isMovie) marvinItem?.movie?.posterPath else marvinItem?.tv?.posterPath
                    val rating =
                        if (isMovie) marvinItem?.movie?.voteAverage else marvinItem?.tv?.voteAverage
                    val voteCount =
                        if (isMovie) marvinItem?.movie?.voteCount else marvinItem?.tv?.voteCount
                    val id =
                        if (isMovie) marvinItem?.movie?.movieId else marvinItem?.tv?.tvId
                    val title =
                        if (isMovie) marvinItem?.movie?.title else marvinItem?.tv?.name
                    val date =
                        if (isMovie) marvinItem?.movie?.releaseDate else marvinItem?.tv?.firstAirDate

                    MovieTvItem(
                        posterPath = posterPath,
                        rating = rating,
                        voteCount = voteCount,
                        itemId = id,
                        itemTitle = title,
                        releasedDate = date,
                        onCardClicked = {
                            onItemClicked(it, isMovie)
                        },
                        onMenuIconClicked = {
                            onMenuIconClicked(it)
                        }
                    )

                }
            }

        }
    }


}