package com.kavrin.marvin.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.kavrin.marvin.presentation.component.PosterWithIcon
import com.kavrin.marvin.presentation.component.RatingIndicator
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.MarvinItem


@Composable
fun <T : MarvinItem> Carousel(
	items: LazyPagingItems<T>,
	isMovie: Boolean,
	onItemClicked: (Int) -> Unit,
	onMenuIconClicked: (Int) -> Unit
) {

	val pagerState = rememberPagerState(
		initialPage = 1
	)

	Column(
		modifier = Modifier
			.fillMaxWidth(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		HorizontalPager(
			count = items.itemCount,
			state = pagerState,
			contentPadding = PaddingValues(
				horizontal = CAROUSEL_HORIZONTAL_PADDING,
				vertical = CAROUSEL_VERTICAL_PADDING
			),
		) { page ->
			val item = items[page]

			val scale = remember {
				Animatable(
					initialValue = 1f
				)
			}
			LaunchedEffect(key1 = pagerState.currentPage) {
				if (page == pagerState.currentPage) {
					scale.animateTo(
						targetValue = 1.1f,
						animationSpec = tween(durationMillis = 700)
					)
				} else {
					scale.animateTo(
						targetValue = 1f,
						animationSpec = tween(durationMillis = 200)
					)
				}
			}

			val posterPath = if (isMovie) item?.movie?.posterPath else item?.tv?.posterPath
			val rating = if (isMovie) item?.movie?.voteAverage else item?.tv?.voteAverage
			val voteCount = if (isMovie) item?.movie?.voteCount else item?.tv?.voteCount
			val itemId = if (isMovie) item?.movie?.movieId else item?.tv?.tvId

			Box {
				PosterWithIcon(
					modifier = Modifier
						.height(CAROUSEL_ITEM_HEIGHT)
						.width(CAROUSEL_ITEM_WIDTH)
						.scale(scale.value)
						.clip(shape = RoundedCornerShape(EXTRA_SMALL_PADDING)),
					posterPath = posterPath,
					itemId = itemId,
					onMenuIconClicked = onMenuIconClicked,
					onItemClicked = onItemClicked
				)

				RatingIndicator(
					modifier = Modifier
						.scale(scale.value)
					    .align(Alignment.BottomStart)
						.offset(x = 5.dp, y = 30.dp),
					canvasSize = 65.dp,
					indicatorValue = rating ?: 0.0,
					smallText = voteCount ?: 0,
					backgroundIndicatorStrokeWidth = 10f,
					foregroundIndicatorStrokeWidth = 10f
				)

			}

		}

		Spacer(modifier = Modifier.height(MEDIUM_PADDING))

		Row(
			modifier = Modifier
				.fillMaxWidth()
				.height(CAROUSEL_TEXT_HEIGHT)
				.padding(horizontal = LARGE_PADDING),
			horizontalArrangement = Arrangement.Center
		) {
			AnimatedVisibility(
				visible = !pagerState.isScrollInProgress,
				enter = fadeIn(
					animationSpec = tween(
						durationMillis = 500
					)
				),
				exit = fadeOut(
					animationSpec = tween(
						durationMillis = 200
					)
				)
			) {
				if (items.itemCount >= 2) {
					val title = if (isMovie) items[pagerState.currentPage]?.movie?.title else items[pagerState.currentPage]?.tv?.name
					title?.let {
						Text(
							text = it,
							fontFamily = fonts,
							fontSize = MaterialTheme.typography.h5.fontSize,
							fontWeight = FontWeight.ExtraBold,
							textAlign = TextAlign.Center,
							maxLines = 2
						)
					}
				}

			}
		}

		Spacer(modifier = Modifier.height(LARGE_PADDING))

		HorizontalPagerIndicator(
			pagerState = pagerState,
			activeColor = BrightMaroon,
			inactiveColor = LightGray,
		)
	}
}
