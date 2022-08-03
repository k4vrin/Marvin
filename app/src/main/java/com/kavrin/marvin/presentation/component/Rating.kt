package com.kavrin.marvin.presentation.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kavrin.marvin.R
import com.kavrin.marvin.R.drawable
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.Constants.IMDB
import com.kavrin.marvin.util.Constants.META
import com.kavrin.marvin.util.Constants.ROTTEN
import com.kavrin.marvin.util.Constants.TMDB
import com.kavrin.marvin.util.ratingProviderLogo
import java.text.DecimalFormat

@Composable
fun Rating(
    ratings: Map<String, String?>,
    ratingState: MutableTransitionState<RatingState>
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = SMALL_PADDING),
            text = stringResource(R.string.ratings),
            fontFamily = nunitoTypeFace,
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.contentColor
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {


            RatingItem(
                rating = ratings[IMDB],
                isPercentage = false,
                provider = IMDB,
                ratingState = ratingState
            )


            RatingItem(
                rating = ratings[META],
                isPercentage = true,
                provider = META,
                ratingState = ratingState
            )

            RatingItem(
                rating = ratings[TMDB],
                isPercentage = false,
                provider = TMDB,
                ratingState = ratingState
            )


            RatingItem(
                rating = ratings[ROTTEN],
                isPercentage = true,
                provider = ROTTEN,
                ratingState = ratingState
            )


        }
    }
}

@Composable
fun RatingItem(
    rating: String?,
    isPercentage: Boolean,
    provider: String,
    ratingState: MutableTransitionState<RatingState>,
    maxIndicatorHeight: Dp = 100.dp,
    maxIndicatorWidth: Dp = 20.dp,
    circleSize: Dp = maxIndicatorWidth,
) {

    val height = with(LocalDensity.current) { maxIndicatorHeight.toPx() }
    val width = with(LocalDensity.current) { maxIndicatorWidth.toPx() }
    val circle = with(LocalDensity.current) { circleSize.toPx() }

    val ratings = if (!rating.isNullOrBlank()) rating.toDouble() else -0.1

    val unifiedRate = remember(rating) {
        if (isPercentage)
            (ratings * height) / 100
        else
            (ratings * height) / 10
    }

    val allowedIndicator by rememberSaveable {
        mutableStateOf(
            unifiedRate.toFloat().coerceIn(minimumValue = 0f, maximumValue = height)
        )
    }

    val animIndicatorValue by rememberSaveable { mutableStateOf(height - allowedIndicator) }

    val painter = painterResource(id = ratingProviderLogo[provider] ?: drawable.retro_tv)

    val indicatorColor = when {
        unifiedRate < 0.0 -> MaterialTheme.colors.ratingBackground
        unifiedRate in 0.0..40.0 -> RatingRed
        unifiedRate in 40.0..70.0 -> RatingYellow
        else -> RatingGreen
    }

    val backgroundColor = MaterialTheme.colors.ratingBackground

    val transition = updateTransition(
        targetState = ratingState,
        label = stringResource(R.string.rating_animation)
    )
    val translateY by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        }, label = stringResource(R.string.rating_translate_y_animation)
    ) { state ->
        when (state.targetState) {
            RatingState.Start -> height
            RatingState.End -> animIndicatorValue
        }
    }

    val content by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        }, label = stringResource(R.string.rating_number_animation)
    ) { state ->
        when (state.targetState) {
            RatingState.Start -> 0f
            RatingState.End -> ratings.toFloat()
        }
    }

    Column(
        modifier = Modifier
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        RatingText(
            modifier = Modifier
                .align(Alignment.End)
                .offset(x = circleSize * 1.2f),
            isPercentage = isPercentage,
            content = content,
            translateY = translateY,
            width = width
        )

        Box(
            modifier = Modifier
                .size(circleSize)
                .zIndex(1f)
                .graphicsLayer {
                    translationY = translateY + (circle / 2)
                }
                .background(
                    brush = indicatorColor,
                    shape = CircleShape
                )
        ) {}

        Canvas(
            modifier = Modifier
                .height(maxIndicatorHeight)
                .width(MEDIUM_PADDING)
        ) {

            indicatorBackground(
                componentSize = size,
                backgroundColor = backgroundColor
            )

            ratingIndicator(
                componentSize = size,
                indicatorColor = indicatorColor,
                endOffset = translateY
            )
        }

        Spacer(modifier = Modifier.height(MEDIUM_PADDING))

        Image(
            modifier = Modifier
                .height(LARGE_PADDING),
            painter = painter,
            contentDescription = stringResource(R.string.rete_logo),
        )

    }

}

private fun DrawScope.ratingIndicator(
    componentSize: Size,
    indicatorColor: Brush,
    endOffset: Float,
) {

    drawLine(
        brush = indicatorColor,
        start = Offset(x = componentSize.center.x, y = componentSize.height),
        end = Offset(x = componentSize.center.x, y = endOffset),
        strokeWidth = componentSize.width,
        cap = StrokeCap.Round,
        alpha = 0.5f,
        blendMode = BlendMode.Color
    )

}

private fun DrawScope.indicatorBackground(
    componentSize: Size,
    backgroundColor: Brush,
) {

    drawLine(
        brush = backgroundColor,
        start = Offset(x = componentSize.center.x, y = componentSize.height),
        end = Offset(x = componentSize.center.x, y = 0f),
        strokeWidth = componentSize.width,
        cap = StrokeCap.Round,
        alpha = 0.3f,
        blendMode = BlendMode.Softlight
    )

}

@Composable
fun RatingText(
    isPercentage: Boolean,
    content: Float,
    translateY: Float,
    width: Float,
    modifier: Modifier = Modifier
) {

    if (content >= 0) {
        val rate = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.cardContentColor,
                    fontFamily = nunitoTypeFace,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
            ) {
                append(
                    text = if (isPercentage) {
                        DecimalFormat("#").format(content)
                    } else {
                        DecimalFormat("#.#").format(content)
                    }
                )
            }

            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.8f),
                    fontFamily = nunitoTypeFace,
                    fontWeight = FontWeight.ExtraLight,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    baselineShift = BaselineShift.Superscript
                )
            ) {
                append(text = if (isPercentage) "%" else "/10")
            }
        }

        Text(
            modifier = modifier
                .width(RATING_TEXT_HEIGHT)
                .height(LARGE_PADDING)
                .graphicsLayer {
                    translationY = translateY + (width / 2)
                },
            text = rate,
            textAlign = TextAlign.Start
        )

    } else {
        Text(
            modifier = modifier
                .width(RATING_TEXT_HEIGHT)
                .height(LARGE_PADDING)
                .graphicsLayer {
                    translationY = translateY + (width / 2)
                },
            text = stringResource(R.string.not_rated),
            color = MaterialTheme.colors.cardContentColor,
            textAlign = TextAlign.Start
        )
    }
}


@Preview(showBackground = true)
@Composable
fun RatingItemPrev() {

    Rating(
        ratings = mapOf(
            IMDB to "6.5",
            TMDB to "4.2",
            META to "90",
            ROTTEN to ""
        ),
        ratingState = MutableTransitionState(RatingState.Start)
    )
}

enum class RatingState {
    Start,
    End
}