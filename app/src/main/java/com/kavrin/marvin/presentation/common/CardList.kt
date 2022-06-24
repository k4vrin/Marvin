package com.kavrin.marvin.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
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
					color = contentColor
				)

				Text(
					text = "See All",
					fontFamily = fonts,
					fontSize = MaterialTheme.typography.subtitle1.fontSize,
					fontWeight = FontWeight.Normal,
					color = contentColor
				)
			}

			LazyRow(
				state = listState,
				contentPadding = PaddingValues(all = MEDIUM_PADDING),
				horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
			) {

				if (isMovie) {

					items(
						items = items,
						key = {
							it.movie?.movieId!!
						}
					) { itemWrapper ->

						val item = itemWrapper?.movie

						MovieTvItem(
							posterPath = item?.posterPath,
							rating = item?.voteAverage,
							voteCount = item?.voteCount,
							itemId = item?.movieId,
							itemTitle = item?.title,
							releasedDate = item?.releaseDate,
							onCardClicked = {},
							onMenuIconClicked = {}
						)

					}

				} else {

					items(
						items = items,
						key = {
							it.tv?.tvId!!
						}
					) { itemWrapper ->

						val item = itemWrapper?.tv

						MovieTvItem(
							posterPath = item?.posterPath,
							rating = item?.voteAverage,
							voteCount = item?.voteCount,
							itemId = item?.tvId,
							itemTitle = item?.name,
							releasedDate = item?.firstAirDate,
							onCardClicked = {},
							onMenuIconClicked = {}
						)

					}
				}
			}

		}
	}


}