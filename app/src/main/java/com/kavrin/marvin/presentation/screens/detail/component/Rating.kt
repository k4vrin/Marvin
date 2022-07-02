package com.kavrin.marvin.presentation.screens.detail.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.presentation.component.RatingIndicator
import com.kavrin.marvin.ui.theme.cardContentColor
import com.kavrin.marvin.ui.theme.fonts

@Composable
fun Rating(
    rating: Double,
    voteCount: Int,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Text(
                text = "Rating",
                fontFamily = fonts,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.cardContentColor
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            RatingIndicator(
                voteAvg = rating,
                voteCount = voteCount,
                canvasSize = 250.dp,
                bigTextFontSize = MaterialTheme.typography.h2.fontSize,
                smallTextFontSize = MaterialTheme.typography.h6.fontSize
            )

        }

    }

}


@Preview
@Composable
fun RatingPrev() {
    Rating(rating = 8.8, voteCount = 534634)
}