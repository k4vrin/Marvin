package com.kavrin.marvin.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MovieTvItem(
	posterPath: String?,
	rating: Double?,
	voteCount: Int?,
	itemId: Int?,
	itemTitle: String?,
	releasedDate: String?,
	onCardClicked: (Int) -> Unit,
	onMenuIconClicked: (Int) -> Unit,
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

			Box(
				modifier = Modifier
					.weight(7f)
			) {
				PosterWithIcon(
					posterPath = posterPath,
					itemId = itemId,
					onMenuIconClicked = onMenuIconClicked,
					onItemClicked = onCardClicked
				)

				RatingIndicator(
					modifier = Modifier
						.align(Alignment.BottomStart)
						.offset(x = 5.dp, y = 30.dp),
					indicatorValue = rating ?: 0.0,
					smallText = voteCount ?: 0,
					canvasSize = 60.dp,
					backgroundIndicatorStrokeWidth = 10f,
					foregroundIndicatorStrokeWidth = 10f,
					bigTextFontSize = MaterialTheme.typography.h6.fontSize
				)

			}

			Spacer(modifier = Modifier.height(LARGE_PADDING))


			Column(
				modifier = Modifier
					.weight(3f)
					.padding(all = MEDIUM_PADDING)
			) {

				if (!itemTitle.isNullOrEmpty() && !releasedDate.isNullOrEmpty()) {
					Text(
						modifier = Modifier
							.fillMaxWidth(),
						text = itemTitle,
						color = MaterialTheme.colors.cardContentColor,
						fontFamily = fonts,
						fontSize = MaterialTheme.typography.body1.fontSize,
						fontWeight = FontWeight.Bold,
						maxLines = 2,
						overflow = TextOverflow.Ellipsis
					)

					Spacer(modifier = Modifier.height(SMALL_PADDING))

					Text(
						modifier = Modifier
							.fillMaxWidth(),
						text = dateFormatter(date = releasedDate),
						color = MaterialTheme.colors.cardContentColor,
						fontFamily = fonts,
						fontSize = MaterialTheme.typography.subtitle2.fontSize,
						fontWeight = FontWeight.Normal,
						maxLines = 1,
						overflow = TextOverflow.Ellipsis
					)

				}

			}

		}


	}

}

@Composable
fun dateFormatter(date: String): String {
	val parsePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
	val formatPattern = DateTimeFormatter.ofPattern("dd MMMM yyyy")
	return LocalDate.parse(date, parsePattern).format(formatPattern)
}


@Preview
@Composable
fun MovieItemPrev() {
	MovieTvItem(
		posterPath = "",
		rating = 8.8,
		voteCount = 46545,
		itemId = 1,
		releasedDate = "",
		itemTitle = "Doctor Strange in the Multiverse of Madness",
		onMenuIconClicked = {},
		onCardClicked = {}
	)
}