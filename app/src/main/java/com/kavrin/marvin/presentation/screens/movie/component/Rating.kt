package com.kavrin.marvin.presentation.screens.movie.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.kavrin.marvin.domain.model.imdb.IMDbRatingApiResponse
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.ratingProviderLogo
import java.text.DecimalFormat

@Composable
fun Rating(
    ratings: IMDbRatingApiResponse,
    tranState: State<TransitionState>
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
                rating = ratings.imDb,
                isPercentage = false,
                provider = "imDb",
                tranState = tranState,
            )


            RatingItem(
                rating = ratings.metacritic,
                isPercentage = true,
                provider = "metacritic",
                tranState = tranState,
            )

            RatingItem(
                rating = ratings.theMovieDb,
                isPercentage = false,
                provider = "theMovieDb",
                tranState = tranState,
            )


            RatingItem(
                rating = ratings.rottenTomatoes,
                isPercentage = true,
                provider = "rottenTomatoes",
                tranState = tranState,
            )


        }
    }
}

@Composable
fun RatingItem(
    rating: String?,
    isPercentage: Boolean,
    provider: String,
    maxIndicatorHeight: Dp = 100.dp,
    maxIndicatorWidth: Dp = 20.dp,
    circleSize: Dp = maxIndicatorWidth,
    tranState: State<TransitionState>
) {

    val height = with(LocalDensity.current) { maxIndicatorHeight.toPx() }
    val width = with(LocalDensity.current) { maxIndicatorWidth.toPx() }
    val circle = with(LocalDensity.current) { circleSize.toPx() }

    val ratings = if (!rating.isNullOrBlank()) rating.toDouble() else -0.1

    val unifiedRate = remember(rating) { if (isPercentage) ratings else ratings * 10 }

    val ratingPercentage = remember(unifiedRate) { (unifiedRate * height) / 100 }

    var allowedIndicator by remember { mutableStateOf(height) }

    allowedIndicator =
        ratingPercentage.toFloat().coerceIn(minimumValue = 0f, maximumValue = height)

    var animIndicatorValue by remember { mutableStateOf(height) }

    LaunchedEffect(key1 = allowedIndicator) {
        animIndicatorValue -= allowedIndicator
    }

    val painter = painterResource(id = ratingProviderLogo[provider] ?: drawable.retro_tv)

    val indicatorColor = when {
        unifiedRate < 0.0 -> MaterialTheme.colors.ratingBackground
        unifiedRate in 0.0..40.0 -> RatingRed
        unifiedRate in 40.0..70.0 -> RatingYellow
        else -> RatingGreen
    }

    val backgroundColor = MaterialTheme.colors.ratingBackground

    val transition = updateTransition(targetState = tranState, label = stringResource(R.string.rating_animation))
    val translateY by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        }, label = stringResource(R.string.rating_translate_y_animation)
    ) { state ->
        when (state.value) {
            TransitionState.Start -> height
            TransitionState.End -> animIndicatorValue
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
        when (state.value) {
            TransitionState.Start -> 0f
            TransitionState.End -> ratings.toFloat()
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
    val state = remember {
        mutableStateOf(TransitionState.End)
    }

    Rating(
        ratings = IMDbRatingApiResponse(
            errorMessage = "" ,
            imDb = "0",
            theMovieDb = "7",
            rottenTomatoes = "66",
            metacritic = "88"
        ),
        tranState = state
    )
}

enum class TransitionState {
    Start,
    End
}