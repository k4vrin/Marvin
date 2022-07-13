package com.kavrin.marvin.presentation.screens.movie.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kavrin.marvin.R
import com.kavrin.marvin.util.Constants

@Composable
fun TrailerItem(
    backdrop: String,
    key: String,
    modifier: Modifier = Modifier,
    onPlayClicked: (key: String) -> Unit
) {

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color.Black.copy(alpha = 0.4f),
            Color.Black.copy(alpha = 0.4f)
        )
    )

    Card(
        modifier = modifier
            .wrapContentSize(),
        onClick = { onPlayClicked(key)},
    ) {
        Box(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithContent {
                        drawContent()
                        drawRect(brush = gradient, blendMode = BlendMode.Multiply)
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${Constants.IMAGE_BASE_URL}${backdrop}")
                    .placeholder(R.drawable.placeholder_dark)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            IconButton(
                modifier = Modifier
                    .background(
                        color = Color.Black.copy(alpha = 0.25f),
                        shape = CircleShape
                    ),
                onClick = {
                    onPlayClicked(key)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White
                )
            }

        }

    }

}


@Preview
@Composable
fun TrailerPreview() {
    TrailerItem(
        backdrop = "/iNh3BivHyg5sQRPP1KOkzguEX0H.jpg",
        key = "PLl99DlL6b4"
    ) {}
}