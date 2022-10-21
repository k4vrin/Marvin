package com.kavrin.marvin.presentation.screens.auth.login.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.cardContentColor

@Composable
fun ContinueSpacer() {

    val colors = listOf(
        MaterialTheme.colors.cardContentColor.copy(alpha = 0f),
        MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
        MaterialTheme.colors.cardContentColor.copy(alpha = 0.6f),
        MaterialTheme.colors.cardContentColor.copy(alpha = 1f),
    )

    val leftLine = Brush.Companion.horizontalGradient(colors)

    val rightLine = Brush.Companion.horizontalGradient(colors.reversed())

    val strokeWidth = 3f

    Row(
        modifier = Modifier
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {

            drawLine(
                brush = leftLine,
                start = Offset(x = 0f, y = size.center.y),
                end = Offset(x = size.width, y = size.center.y),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round,
                blendMode = BlendMode.Multiply
            )
        }

        Text(
            modifier = Modifier
                .padding(horizontal = SMALL_PADDING),
            text = "Or continue with",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.cardContentColor
        )

        Canvas(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            drawLine(
                brush = rightLine,
                start = Offset(x = 0f, y = size.height / 2),
                end = Offset(x = size.width, y = size.height / 2),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round,
                blendMode = BlendMode.Multiply
            )
        }


    }

}

@Preview(showBackground = true)
@Composable
fun CustomLoginSpacerPreview() {
    ContinueSpacer()
}