package com.kavrin.marvin.presentation.screens.movie.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.ui.theme.*

@Composable
fun CollectionHeader(
    collectionName: String,
    collectionOverview: String
) {


    Column(
        modifier = Modifier
            .height(MAIN_CARD_HEIGHT)
            .width(220.dp)
            .background(
                brush = MaterialTheme.colors.cardGradientColor,
                shape = RoundedCornerShape(size = MEDIUM_PADDING),
                alpha = 0.6f
            )
            .padding(all = SMALL_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Text(
            text = collectionName,
            fontFamily = nunitoTypeFace,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h5.fontSize,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.contentColor
        )

        Text(
            text = collectionOverview,
            fontFamily = nunitoTypeFace,
            fontWeight = FontWeight.SemiBold,
            fontSize = MaterialTheme.typography.body1.fontSize,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.contentColor,
            overflow = TextOverflow.Ellipsis
        )

    }
}


@Preview
@Composable
fun CollectionHeaderPreview() {
    CollectionHeader(
        collectionName = "Thor Collection",
        collectionOverview = "A superhero film series based on the comic book character of the same name published by Marvel Comics, and part of the Marvel Cinematic Universe (MCU) film series. The series centers on Thor, the crown prince of Asgard."
    )
}