package com.kavrin.marvin.presentation.screens.person.components

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.presentation.component.PosterWithIcon
import com.kavrin.marvin.presentation.component.RatingIndicator
import com.kavrin.marvin.ui.theme.*

@Composable
fun PersonCreditItem(
    posterPath: String?,
    itemId: Int?,
    rating: Double?,
    voteCount: Int?,
    itemTitle: String?,
    subText: String?,
    modifier: Modifier = Modifier,
    episodeCount: Int? = null,
    shape: Shape = RoundedCornerShape(size = MEDIUM_PADDING),
    onItemClicked: (Int) -> Unit,
    onMenuIconClicked: (Int) -> Unit
) {


    Card(
        modifier = modifier
            .height(MAIN_CARD_HEIGHT)
            .width(MAIN_CARD_WIDTH)
            .shadow(
                shape = shape,
                elevation = 2.dp
            ),
        shape = shape,
        backgroundColor = MaterialTheme.colors.cardColor,
        onClick = {
            itemId?.let(onItemClicked)
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
                    enableAnimation = true,
                    canvasSize = CARD_CANVAS_SIZE,
                    backgroundIndicatorStrokeWidth = 10f,
                    foregroundIndicatorStrokeWidth = 10f,
                    bigTextFontSize = MaterialTheme.typography.h6.fontSize
                )

                if (episodeCount != null) {
                    val episodes = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colors.cardContentColor,
                                fontFamily = nunitoTypeFace,
                                fontSize = MaterialTheme.typography.caption.fontSize,
                                fontWeight = FontWeight.Bold,
                            )
                        ) {
                            append(text = "$episodeCount")
                        }

                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colors.cardContentColor,
                                fontFamily = nunitoTypeFace,
                                fontSize = MaterialTheme.typography.caption.fontSize,
                                fontWeight = FontWeight.Normal,
                            )
                        ) {
                            append(text = if (episodeCount > 1) " Episodes" else " Episode")
                        }
                    }

                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = -(RATING_CAROUSEL_X_OFFSET), y = RATING_CAROUSEL_Y_OFFSET),
                        text = episodes
                    )


                }

            }

            Spacer(modifier = Modifier.height(LARGE_PADDING))


            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(all = MEDIUM_PADDING)
            ) {

                if (!itemTitle.isNullOrEmpty() && !subText.isNullOrEmpty()) {
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
                        text = subText,
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