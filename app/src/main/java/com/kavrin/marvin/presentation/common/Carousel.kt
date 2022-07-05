package com.kavrin.marvin.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
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
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.kavrin.marvin.presentation.component.PosterWithIcon
import com.kavrin.marvin.presentation.component.RatingIndicator
import com.kavrin.marvin.presentation.screens.home.handlePagingResult
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.MarvinItem


@Composable
fun <T : MarvinItem> Carousel(
    items: LazyPagingItems<T>,
    isMovie: Boolean,
    onItemClicked: (Int, Boolean) -> Unit,
    onMenuIconClicked: (Int) -> Unit
) {

    val pagerState = rememberPagerState(
        initialPage = 2
    )

    val result = handlePagingResult(item = items, isCarousel = true)

    if (result) {

        ///// Container /////
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ///// Pager /////
            HorizontalPager(
                count = items.itemCount,
                state = pagerState,
                contentPadding = PaddingValues(
                    horizontal = CAROUSEL_HORIZONTAL_PADDING,
                    vertical = CAROUSEL_VERTICAL_PADDING
                ),
            ) { page ->
                val item = items[page]

                ///// Animate Scale /////
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

                ///// Each Carousel Item Container /////
                Box {
                    ///// Poster /////
                    PosterWithIcon(
                        modifier = Modifier
                            .height(CAROUSEL_ITEM_HEIGHT)
                            .width(CAROUSEL_ITEM_WIDTH)
                            .scale(scale.value)
                            .clip(shape = RoundedCornerShape(EXTRA_SMALL_PADDING))
                            .clickable {
                                if (itemId != null)
                                    onItemClicked(itemId, isMovie)
                            },
                        posterPath = posterPath,
                        itemId = itemId,
                        onMenuIconClicked = onMenuIconClicked,
                    )

                    ///// Rating /////
                    RatingIndicator(
                        modifier = Modifier
                            .scale(scale.value)
                            .align(Alignment.BottomStart)
                            .offset(x = RATING_CAROUSEL_X_OFFSET, y = RATING_CAROUSEL_Y_OFFSET),
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

                val title = if (isMovie) items[pagerState.currentPage]?.movie?.title
                    ?: "" else items[pagerState.currentPage]?.tv?.name ?: ""

                Spacer(modifier = Modifier.height(MEDIUM_PADDING))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(CAROUSEL_TEXT_HEIGHT)
                        .padding(horizontal = LARGE_PADDING),
                    horizontalArrangement = Arrangement.Center
                ) {

                    AnimatedVisibility(
                        modifier = Modifier
                            .fillMaxWidth(),
                        visible = !pagerState.isScrollInProgress,
                        enter = scaleIn(
                            animationSpec = tween(
                                durationMillis = 500
                            )
                        ),
                        exit = scaleOut(
                            animationSpec = tween(
                                durationMillis = 100
                            )
                        )
                    ) {
                        Text(
                            text = title,
                            fontFamily = fonts,
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
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = MaterialTheme.colors.pagerIndicatorActiveColor,
                inactiveColor = LightGray,
            )
        }

    }
}
