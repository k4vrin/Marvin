package com.kavrin.marvin.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.kavrin.marvin.ui.theme.cardContentColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace

@Composable
fun Overview(
    overview: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = overview,
            fontFamily = nunitoTypeFace,
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colors.cardContentColor,
            lineHeight = 24.sp
        )

    }

}

@Preview
@Composable
fun OverviewPrev() {
    Overview(overview = "lorem ipsum dolor sit amet lorem ipsum dolor sit amet lorem ipsum dolor sit amet lorem ipsum dolor sit amet lorem ipsum dolor sit amet")
}