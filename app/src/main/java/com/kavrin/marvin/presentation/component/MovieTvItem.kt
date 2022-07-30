package com.kavrin.marvin.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.dateFormatter

@Composable
fun MovieTvItem(
    posterPath: String?,
    rating: Double?,
    voteCount: Int?,
    itemId: Int?,
    itemTitle: String?,
    shape: Shape = RoundedCornerShape(size = MEDIUM_PADDING),
    releasedDate: String?,
    onCardClicked: (Int) -> Unit,
    onMenuIconClicked: (Int) -> Unit,
) {

    Card(
        modifier = Modifier
            .height(MAIN_CARD_HEIGHT)
            .width(MAIN_CARD_WIDTH)
            .shadow(
                shape = shape,
                elevation = 2.dp
            ),
        shape = shape,
        backgroundColor = MaterialTheme.colors.secondaryCardColor,
        onClick = {
            itemId?.let(onCardClicked)
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .weight(7f)
            ) {
                PosterWithIcon(
                    posterPath = posterPath,
                    itemId = itemId,
                    onMenuIconClicked = onMenuIconClicked,
                )

                RatingIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset(x = RATING_CAROUSEL_X_OFFSET, y = RATING_CAROUSEL_Y_OFFSET),
                    voteAvg = rating,
                    voteCount = voteCount,
                    enableAnimation = true ,
                    canvasSize = CARD_CANVAS_SIZE,
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
                        fontFamily = nunitoTypeFace,
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
                        fontFamily = nunitoTypeFace,
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


@Preview
@Composable
fun MovieItemPrev() {
    MovieTvItem(
        posterPath = "",
        rating = 8.8,
        voteCount = 46545,
        itemId = 1,
        releasedDate = "2022-02-02",
        itemTitle = "Doctor Strange in the Multiverse of Madness",
        onMenuIconClicked = {},
        onCardClicked = {}
    )
}