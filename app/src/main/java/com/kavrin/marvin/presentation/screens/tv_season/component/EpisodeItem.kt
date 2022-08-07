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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kavrin.marvin.R
import com.kavrin.marvin.domain.use_cases.tv_season.EpisodeSummary
import com.kavrin.marvin.presentation.component.RatingIndicator
import com.kavrin.marvin.ui.theme.CARD_CANVAS_SIZE
import com.kavrin.marvin.ui.theme.LARGE_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.nunitoTypeFace
import com.kavrin.marvin.util.Constants

@Composable
fun EpisodeItem(
    episodeSummary: EpisodeSummary,
    contentColor: Color = Color.White,
    onEpisodeClicked: (Int) -> Unit
) {

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.Black.copy(alpha = 0.4f),
            Color.Black.copy(alpha = 0.8f)
        )
    )

    Card(
        onClick = {
            onEpisodeClicked(episodeSummary.id)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithContent {
                        drawContent()
                        drawRect(brush = gradient, blendMode = BlendMode.Multiply)
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${Constants.IMAGE_BACKDROP_BASE_URL}${episodeSummary.stillPath}")
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
                    .padding(horizontal = SMALL_PADDING, vertical = LARGE_PADDING),
                verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
            ) {

                Text(
                    text = "Episode ${episodeSummary.episodeNumber}",
                    fontFamily = nunitoTypeFace,
                    fontSize = MaterialTheme.typography.subtitle2.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = contentColor
                )

                Text(
                    text = episodeSummary.name,
                    fontFamily = nunitoTypeFace,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    fontWeight = FontWeight.Black,
                    color = contentColor
                )

            }

            RatingIndicator(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(all = SMALL_PADDING),
                canvasSize = CARD_CANVAS_SIZE,
                enableAnimation = true,
                backgroundIndicatorStrokeWidth = 10f,
                foregroundIndicatorStrokeWidth = 10f,
                voteAvg = episodeSummary.voteAverage,
                voteCount = episodeSummary.voteCount,
                bigTextFontSize = MaterialTheme.typography.h6.fontSize
            )

        }

    }


}

@Preview
@Composable
fun EpisodeItemPreview() {
    EpisodeItem(
        episodeSummary = EpisodeSummary(
            id = 1,
            overview = "On his way home from a friend's house, young Will sees something terrifying. Nearby, a sinister secret lurks in the depths of a government lab.",
            stillPath = "",
            name = "Chapter One: The Vanishing of Will Byers",
            voteAverage = 8.8,
            voteCount = 100,
            episodeNumber = 1
        ),
        contentColor = Color.White,
        onEpisodeClicked = {}
    )


}