package com.kavrin.marvin.presentation.screens.home.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.kavrin.marvin.presentation.component.PosterWithIcon
import com.kavrin.marvin.presentation.component.RatingIndicator
import com.kavrin.marvin.presentation.component.WormHorizontalPagerIndicator
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.Constants
import com.kavrin.marvin.util.MarvinItem
import com.kavrin.marvin.util.lerp
import kotlin.math.absoluteValue


@Composable
fun <T : MarvinItem> Carousel(
    items: LazyPagingItems<T>,
    isMovie: Boolean,
    onItemClicked: (Int) -> Unit,
    onMenuIconClicked: (Int) -> Unit
) {

    val pagerState = rememberPagerState(
        initialPage = 2
    )


    ///// Container /////
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 400.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ///// Pager /////
        HorizontalPager(
            modifier = Modifier
                .heightIn(min = 300.dp),
            count = items.itemCount,
            state = pagerState,
            contentPadding = PaddingValues(
                horizontal = CAROUSEL_HORIZONTAL_PADDING,
                vertical = CAROUSEL_VERTICAL_PADDING
            ),
//                key = { page ->
//                    val key = if (isMovie) items[page]?.movie?.movieId else items[page]?.tv?.tvId
//                    key!!
//                }
        ) { page ->
            val item = items[page]

            val posterPath =
                remember { if (isMovie) item?.movie?.posterPath else item?.tv?.posterPath }
            val rating =
                remember { if (isMovie) item?.movie?.voteAverage else item?.tv?.voteAverage }
            val voteCount =
                remember { if (isMovie) item?.movie?.voteCount else item?.tv?.voteCount }
            val itemId =
                remember { if (isMovie) item?.movie?.movieId else item?.tv?.tvId }

            ///// Each Carousel Item Container /////
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scaleAndAlpha ->
                            scaleX = scaleAndAlpha
                            scaleY = scaleAndAlpha
                        }
                    }
            ) {
                ///// Poster /////
                PosterWithIcon(
                    modifier = Modifier
                        .height(CAROUSEL_ITEM_HEIGHT)
                        .width(CAROUSEL_ITEM_WIDTH)
                        .graphicsLayer {
                            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                            alpha = lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                        .clip(shape = RoundedCornerShape(EXTRA_SMALL_PADDING))
                        .clickable {
                            if (itemId != null)
                                onItemClicked(itemId)
                        },
                    posterPath = posterPath,
                    itemId = itemId,
                    imageBaseUrl = Constants.IMAGE_CAROUSEL_POSTER_BASE_URL,
                    onMenuIconClicked = onMenuIconClicked,
                )

                ///// Rating /////
                RatingIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset(x = RATING_CAROUSEL_X_OFFSET, y = RATING_CAROUSEL_Y_OFFSET)
                        .graphicsLayer {
                            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                            alpha = lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        },
                    canvasSize = CAROUSEL_CANVAS_SIZE,
                    voteAvg = rating,
                    voteCount = voteCount,
                    enableAnimation = true,
                    backgroundIndicatorStrokeWidth = 10f,
                    foregroundIndicatorStrokeWidth = 10f
                )
            }
        }

        // First Visible Carousel is number 2
        if (items.itemCount > 1) {

            val title = remember(pagerState.currentPage) {
                if (isMovie) items[pagerState.currentPage]?.movie?.title
                    ?: "" else items[pagerState.currentPage]?.tv?.name ?: ""
            }

            Spacer(modifier = Modifier.height(MEDIUM_PADDING))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CAROUSEL_TEXT_HEIGHT)
                    .padding(horizontal = LARGE_PADDING),
                horizontalArrangement = Arrangement.Center
            ) {

                AnimatedContent(targetState = title) { targetTitle ->

                    Text(
                        text = targetTitle,
                        fontFamily = nunitoTypeFace,
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        color = MaterialTheme.colors.contentColor
                    )

                }

            }

        }

        Spacer(modifier = Modifier.height(LARGE_PADDING))

        ///// Pager Indicator /////
        WormHorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = MaterialTheme.colors.pagerIndicatorActiveColor,
        )
    }

}
