package com.kavrin.marvin.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.kavrin.marvin.ui.theme.*

@Composable
fun MovieTvItem(
	posterPath: String?,
	rating: Double?,
	voteCount: Int?,
	itemId: Int?,
	itemTitle: String?,
	onCardClicked: (Int) -> Unit,
	onMenuIconClicked: (Int) -> Unit
) {

	Card(
		modifier = Modifier
			.height(MAIN_CARD_HEIGHT)
			.width(MAIN_CARD_WIDTH)
			.clip(shape = RoundedCornerShape(size = MEDIUM_PADDING)),
		onClick = { itemId?.let(onCardClicked) }
	) {

		Column(
			modifier = Modifier
				.fillMaxSize()
				.background(color = MaterialTheme.colors.cardColor)
		) {

			PosterWithRating(
				modifier = Modifier
				    .weight(8f),
				posterPath = posterPath,
				rating = rating,
				itemId = itemId,
				count = voteCount,
				onMenuIconClicked = onMenuIconClicked,
				onItemClicked = onCardClicked
			)

			Column(
				modifier = Modifier
					.weight(2f)
					.padding(all = MEDIUM_PADDING)
			) {

				if (itemTitle != null) {
					Text(
						text = itemTitle,
						color = MaterialTheme.colors.cardContentColor,
						fontFamily = fonts,
						fontSize = MaterialTheme.typography.h6.fontSize
					)
				}

			}

		}


	}

}


@Preview
@Composable
fun MovieItemPrev() {
	MovieTvItem(
		posterPath = "",
		rating = 8.8,
		voteCount = 46545,
		itemId = 1,
		itemTitle = "Doctor Strange in the Multiverse of Madness",
		onMenuIconClicked = {},
		onCardClicked = {}
	)
}