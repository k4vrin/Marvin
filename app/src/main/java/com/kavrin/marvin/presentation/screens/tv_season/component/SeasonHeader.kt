package com.kavrin.marvin.presentation.screens.tv_season.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.EXTRA_LARGE_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.nunitoTypeFace
import com.kavrin.marvin.util.Constants

@Composable
fun SeasonHeader(
    poster: String?,
    seasonName: String?,
    seasonOverview: String?,
    contentColor: Color = Color.White
) {

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.Black.copy(alpha = 0.4f),
            Color.Black.copy(alpha = 0.8f)
        )
    )

    Card {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithContent {
                        drawContent()
                        drawRect(brush = gradient, blendMode = BlendMode.Multiply)
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${Constants.IMAGE_BACKDROP_BASE_URL}${poster}")
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.episode_pic),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = SMALL_PADDING, vertical = EXTRA_LARGE_PADDING),
                verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
            ) {

                if (!seasonName.isNullOrBlank()) {
                    Text(
                        text = seasonName,
                        fontFamily = nunitoTypeFace,
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Black,
                        color = contentColor
                    )

                }

                if (!seasonOverview.isNullOrBlank()) {
                    Text(
                        text = seasonOverview,
                        fontFamily = nunitoTypeFace,
                        fontSize = MaterialTheme.typography.body1.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        color = contentColor
                    )

                }

            }
        }
    }
}