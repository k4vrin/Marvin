package com.kavrin.marvin.presentation.screens.person.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.kavrin.marvin.ui.theme.MAIN_CARD_HEIGHT
import com.kavrin.marvin.ui.theme.cardContentColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace
import com.kavrin.marvin.ui.theme.secondaryCardColor

@Composable
fun PersonStickyHeader(
    title: String
) {

    Card(
        modifier = Modifier
            .height(MAIN_CARD_HEIGHT),
        backgroundColor = MaterialTheme.colors.secondaryCardColor
    ) {

        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .rotate(-90f),
                text = title,
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colors.cardContentColor
            )
        }

    }

}


@Preview(showBackground = true)
@Composable
fun StickyHeaderPreview() {
    PersonStickyHeader(title = "Cast")
}