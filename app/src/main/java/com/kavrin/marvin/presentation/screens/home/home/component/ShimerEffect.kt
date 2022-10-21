package com.kavrin.marvin.presentation.screens.home.home.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.ui.theme.*


@Composable
fun ShimmerCarouselEffect() {

	val transition = rememberInfiniteTransition()
	val alphaAnim by transition.animateFloat(
		initialValue = 1f,
		targetValue = 0f,
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = 500,
				easing = LinearEasing
			),
			repeatMode = RepeatMode.Reverse
		)
	)

	ShimmerCarouselItem(alpha = alphaAnim)

}

@Composable
fun ShimmerCardEffect() {

	val transition = rememberInfiniteTransition()
	val alphaAnim by transition.animateFloat(
		initialValue = 1f,
		targetValue = 0f,
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = 500,
				easing = LinearEasing
			),
			repeatMode = RepeatMode.Reverse
		)
	)

	ShimmerCardListItem(alpha = alphaAnim)

}


@Composable
fun ShimmerCarouselItem(
	alpha: Float,
) {

	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(CAROUSEL_ITEM_HEIGHT * 1.3f)
			.padding(vertical = MEDIUM_PADDING)
	) {

		Box(
			modifier = Modifier
				.alpha(alpha)
				.fillMaxWidth(0.2f)
				.height(CAROUSEL_ITEM_HEIGHT)
				.background(
					color = MaterialTheme.colors.cardShimmerColor,
					shape = RoundedCornerShape(
						topEnd = SMALL_PADDING,
						bottomEnd = SMALL_PADDING
					)
				)
		) {}

		Spacer(modifier = Modifier.width(EXTRA_LARGE_PADDING))

		Box(
			modifier = Modifier
				.alpha(alpha)
				.fillMaxWidth(0.6f)
				.height(CAROUSEL_ITEM_HEIGHT)
				.background(
					color = MaterialTheme.colors.cardShimmerColor,
					shape = RoundedCornerShape(
						size = SMALL_PADDING
					)
				)
		) {

			Row(
				modifier = Modifier
					.align(Alignment.BottomCenter)
					.fillMaxWidth()
					.fillMaxHeight(0.3f)
					.padding(horizontal = MEDIUM_PADDING),
				verticalAlignment = Alignment.CenterVertically
			) {

				Surface(
					modifier = Modifier
						.alpha(alpha)
						.size(48.dp),
					color = MaterialTheme.colors.ratingShimmerColor,
					shape = CircleShape
				) {}

			}
		}

		Spacer(modifier = Modifier.width(EXTRA_LARGE_PADDING))

		Box(
			modifier = Modifier
				.alpha(alpha)
				.fillMaxWidth()
				.height(CAROUSEL_ITEM_HEIGHT)
				.background(
					color = MaterialTheme.colors.cardShimmerColor,
					shape = RoundedCornerShape(
						topStart = SMALL_PADDING,
						bottomStart = SMALL_PADDING
					)
				)
		) {

			Row(
				modifier = Modifier
					.align(Alignment.BottomCenter)
					.fillMaxWidth()
					.fillMaxHeight(0.3f)
					.padding(horizontal = MEDIUM_PADDING),
				verticalAlignment = Alignment.CenterVertically
			) {

				Surface(
					modifier = Modifier
						.alpha(alpha)
						.size(48.dp),
					color = MaterialTheme.colors.ratingShimmerColor,
					shape = CircleShape
				) {}

			}
		}

	}

}

@Composable
fun ShimmerCardListItem(
	alpha: Float,
) {

	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(MAIN_CARD_HEIGHT * 1.3f),
		verticalAlignment = Alignment.CenterVertically
	) {

		Box(
			modifier = Modifier
				.alpha(alpha)
				.width(MAIN_CARD_WIDTH * 1.1f)
				.height(MAIN_CARD_HEIGHT)
				.background(
					color = MaterialTheme.colors.cardShimmerColor,
					shape = RoundedCornerShape(
						size = SMALL_PADDING
					)
				)
		) {

			Row(
				modifier = Modifier
					.align(Alignment.BottomCenter)
					.fillMaxWidth()
					.fillMaxHeight(0.3f)
					.padding(horizontal = MEDIUM_PADDING),
				verticalAlignment = Alignment.CenterVertically
			) {

				Surface(
					modifier = Modifier
						.alpha(alpha)
						.size(48.dp),
					color = MaterialTheme.colors.ratingShimmerColor,
					shape = CircleShape
				) {}

			}
		}

		Spacer(modifier = Modifier.width(MEDIUM_PADDING))

		Box(
			modifier = Modifier
				.alpha(alpha)
				.width(MAIN_CARD_WIDTH * 1.1f)
				.height(MAIN_CARD_HEIGHT)
				.background(
					color = MaterialTheme.colors.cardShimmerColor,
					shape = RoundedCornerShape(
						size = SMALL_PADDING
					)
				)
		) {

			Row(
				modifier = Modifier
					.align(Alignment.BottomCenter)
					.fillMaxWidth()
					.fillMaxHeight(0.3f)
					.padding(horizontal = MEDIUM_PADDING),
				verticalAlignment = Alignment.CenterVertically
			) {

				Surface(
					modifier = Modifier
						.alpha(alpha)
						.size(48.dp),
					color = MaterialTheme.colors.ratingShimmerColor,
					shape = CircleShape
				) {}

			}

		}

	}

}


@Preview
@Composable
fun ShimmerItemPrev() {
	ShimmerCarouselItem(
		0.5F
	)
}

@Preview
@Composable
fun ShimmerItem2Prev() {
	ShimmerCardListItem(alpha = 0.5f)
}