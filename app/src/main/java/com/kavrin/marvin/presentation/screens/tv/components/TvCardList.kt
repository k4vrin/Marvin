package com.kavrin.marvin.presentation.screens.tv.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.presentation.component.MovieTvItem
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.contentColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace

@Composable
fun TvCardList(
    lazyListState: LazyListState,
    cardListTitle: String,
    items: List<Tv>,
    onTvClicked: (Int) -> Unit,
    onMenuClicked: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MEDIUM_PADDING),
        ) {

            Text(
                text = cardListTitle,
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.contentColor
            )
        }

        LazyRow(
            state = lazyListState,
            contentPadding = PaddingValues(horizontal = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {

            items(
                items = items,
                key = {
                    it.tvId
                }
            ) { tv ->

                MovieTvItem(
                    posterPath = tv.posterPath,
                    rating = tv.voteAverage,
                    voteCount = tv.voteCount,
                    itemId = tv.tvId,
                    itemTitle = tv.name,
                    releasedDate = tv.firstAirDate,
                    onCardClicked = {
                        onTvClicked(it)
                    },
                    onMenuIconClicked = {
                        onMenuClicked(it)
                    }
                )

            }
        }


    }


}