package com.kavrin.marvin.presentation.screens.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.kavrin.marvin.R
import com.kavrin.marvin.presentation.component.MovieTvItem
import com.kavrin.marvin.presentation.component.SeeAllCard
import com.kavrin.marvin.presentation.screens.home.handlePagingResult
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.contentColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace
import com.kavrin.marvin.util.MarvinItem

@Composable
fun <T : MarvinItem> CardList(
    cardListTitle: String,
    items: LazyPagingItems<T>,
    isMovie: Boolean,
    seeAllEnabled: Boolean = true,
    onItemClicked: (Int) -> Unit,
    onMenuIconClicked: (Int) -> Unit,
    onSeeAllClicked: ((String) -> Unit)? = null
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
                    fontFamily = nunitoTypeFace,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.contentColor
                )

                if (seeAllEnabled) {
                    Text(
                        modifier = Modifier
                            .clickable {
                                if (onSeeAllClicked != null) {
                                    onSeeAllClicked(cardListTitle)
                                }
                            },
                        text = stringResource(R.string.see_all),
                        fontFamily = nunitoTypeFace,
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colors.contentColor
                    )
                }
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
                        remember { if (isMovie) marvinItem?.movie?.posterPath else marvinItem?.tv?.posterPath }
                    val rating =
                        remember { if (isMovie) marvinItem?.movie?.voteAverage else marvinItem?.tv?.voteAverage }
                    val voteCount =
                        remember { if (isMovie) marvinItem?.movie?.voteCount else marvinItem?.tv?.voteCount }
                    val id =
                        remember { if (isMovie) marvinItem?.movie?.movieId else marvinItem?.tv?.tvId }
                    val title =
                        remember { if (isMovie) marvinItem?.movie?.title else marvinItem?.tv?.name }
                    val date =
                        remember { if (isMovie) marvinItem?.movie?.releaseDate else marvinItem?.tv?.firstAirDate }


                    MovieTvItem(
                        posterPath = posterPath,
                        rating = rating,
                        voteCount = voteCount,
                        itemId = id,
                        itemTitle = title,
                        releasedDate = date,
                        onCardClicked = {
                            onItemClicked(it)
                        },
                        onMenuIconClicked = {
                            onMenuIconClicked(it)
                        }
                    )

                }

                item {
                    SeeAllCard {
                        if (onSeeAllClicked != null) {
                            onSeeAllClicked(cardListTitle)
                        }
                    }
                }


            }

        }
    }


}