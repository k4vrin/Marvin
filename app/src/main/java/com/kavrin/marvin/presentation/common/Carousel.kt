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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.kavrin.marvin.R
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.Constants

@Composable
fun MovieCarousel(
	items: LazyPagingItems<MovieAndTrending>,
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
			val movie = items[page]

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
						animationSpec = tween(durationMillis = 700)
					)
				}
			}

			if (items.itemCount>= 1) {
				CarouselItem(
					posterPath = movie?.movie?.posterPath?: "",
					rating = movie?.movie?.voteAverage?:0.0,
					voteCount = movie?.movie?.voteCount?:0,
					itemId = movie?.movie?.movieId?:0,
					scale = scale.value,
					onItemClicked = onItemClicked,
					onMenuIconClicked = onMenuIconClicked
				)
			}
		}

		Spacer(modifier = Modifier.height(LARGE_PADDING))

		Row(
			modifier = Modifier
				.fillMaxWidth()
				.height(48.dp)
			    .padding(horizontal = LARGE_PADDING),
			horizontalArrangement = Arrangement.Center
		) {
			AnimatedVisibility(
				visible = !pagerState.isScrollInProgress,
				enter = fadeIn(),
				exit = fadeOut()
			) {
				if (items.itemCount>= 1) {
					Text(
						text = items[pagerState.currentPage]?.movie?.title ?: "",
						fontFamily = fonts,
						fontSize = MaterialTheme.typography.h6.fontSize,
						fontWeight = FontWeight.Bold,
						textAlign = TextAlign.Center
					)
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

@Composable
fun CarouselItem(
	posterPath: String,
	rating: Double,
	voteCount: Int,
	itemId: Int,
	scale: Float,
	modifier: Modifier = Modifier,
	onItemClicked: (Int) -> Unit,
	onMenuIconClicked: (Int) -> Unit
) {
	val painter = rememberAsyncImagePainter(
		model = "${Constants.IMAGE_BASE_URL}${posterPath}",
		placeholder = painterResource(id = R.drawable.placeholder),
		error = painterResource(id = R.drawable.placeholder)
	)

	Column(
		modifier = modifier
		    .padding(horizontal = 5.dp)
	) {
		PosterWithRating(
			modifier = Modifier
				.height(300.dp)
				.width(200.dp)
				.scale(scale)
				.clip(shape = RoundedCornerShape(EXTRA_SMALL_PADDING)),
			painter = painter,
			rating = rating,
			count = voteCount,
			itemId = itemId,
			onMenuIconClicked = onMenuIconClicked,
			onItemClicked = onItemClicked
		)
	}
}

@Preview
@Composable
fun CarouselItemPreview() {
	CarouselItem(
		posterPath = "",
		rating = 6.6,
		voteCount = 12356,
		itemId = 4,
		scale = 1f,
		onItemClicked = {},
		onMenuIconClicked = {}
	)
}


















