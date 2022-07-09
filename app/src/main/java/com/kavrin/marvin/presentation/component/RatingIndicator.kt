package com.kavrin.marvin.presentation.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.ui.theme.*
import java.text.DecimalFormat

@Composable
fun RatingIndicator(
    voteAvg: Double?,
    voteCount: Int?,
    modifier: Modifier = Modifier,
    enableAnimation: Boolean = false,
    canvasSize: Dp = 300.dp,
    maxIndicatorValue: Float = 10f,
    backgroundIndicatorStrokeWidth: Float = 50f,
    foregroundIndicatorStrokeWidth: Float = 50f,
    bigTextFontSize: TextUnit = MaterialTheme.typography.h5.fontSize,
    bigTextColor: Color = Color.White,
    smallTextFontSize: TextUnit = MaterialTheme.typography.overline.fontSize,
    smallTextColor: Color = Color.White.copy(alpha = 0.5f),
) {

    ///// Handle null /////
    val mVoteAvg = remember {
        voteAvg ?: 0.0
    }
    val mVoteCount = remember {
        voteCount ?: 0
    }

    ///// Handle Vote Max /////
    var allowedIndicatorValue by remember {
        mutableStateOf(maxIndicatorValue)
    }
    allowedIndicatorValue = if (mVoteAvg <= maxIndicatorValue)
        DecimalFormat("#.#").format(mVoteAvg).toFloat()
    else
        maxIndicatorValue

    ///// Assign Anim Value When voteAvg change /////
    var animIndicatorValue by remember {
        mutableStateOf(0f)
    }
    LaunchedEffect(key1 = allowedIndicatorValue) {
        animIndicatorValue = allowedIndicatorValue
    }

    ///// Animate Vote /////
    val targetValue = remember(key1 = animIndicatorValue) {
        ((animIndicatorValue / maxIndicatorValue) * 100) * 3.6f
    }

//	val sweepAngle by animateFloatAsState(
//		targetValue = (3.6 * percentage).toFloat(),
//		animationSpec = tween(durationMillis = 1000)
//	)

    val sweepAngle by if (enableAnimation) {
        animateFloatAsState(
            targetValue = targetValue,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
    } else {
        rememberUpdatedState(newValue = targetValue)
    }
    ///// Foreground Color /////
    val foregroundIndicatorColor = remember {
        when (allowedIndicatorValue) {
            in 0f..4f -> LowRate
            in 4f..7f -> MediumRate
            else -> HighRate
        }
    }
    ///// BackGround Color /////
    val backgroundIndicatorColor = foregroundIndicatorColor.copy(alpha = 0.3f)

    ///// Container /////
    Column(
        modifier = modifier
            .size(canvasSize)
            .background(
                color = ShimmerDarkGray,
                shape = CircleShape
            )
            .drawBehind {
                val componentSize = size / 1.25f
                backgroundIndicator(
                    componentSize = componentSize,
                    indicatorColor = backgroundIndicatorColor,
                    indicatorStrokeWidth = backgroundIndicatorStrokeWidth
                )
                foregroundIndicator(
                    sweepAngle = sweepAngle,
                    componentSize = componentSize,
                    indicatorColor = foregroundIndicatorColor,
                    indicatorStrokeWidth = foregroundIndicatorStrokeWidth
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EmbeddedElements(
            bigText = allowedIndicatorValue,
            bigTextColor = bigTextColor,
            bigTextFontSize = bigTextFontSize,
            smallText = mVoteCount,
            smallTextColor = smallTextColor,
            smallTextFontSize = smallTextFontSize
        )

    }
}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 270f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

@Composable
fun EmbeddedElements(
    bigText: Float,
    bigTextColor: Color,
    bigTextFontSize: TextUnit,
    smallText: Int,
    smallTextColor: Color,
    smallTextFontSize: TextUnit,
) {


    Text(
        text = bigText.toString(),
        fontFamily = nunitoTypeFace,
        color = bigTextColor,
        fontSize = bigTextFontSize,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )

    Text(
        text = smallText.toString(),
        fontFamily = nunitoTypeFace,
        color = smallTextColor,
        fontSize = smallTextFontSize,
        textAlign = TextAlign.Center
    )

}


@Preview()
@Composable
fun RatingComponentPrev() {
    RatingIndicator(
        voteAvg = 8.8,
        voteCount = 4654,
        bigTextFontSize = MaterialTheme.typography.h1.fontSize,
        smallTextFontSize = MaterialTheme.typography.h4.fontSize,
    )
}