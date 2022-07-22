package com.kavrin.marvin.presentation.screens.tv.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kavrin.marvin.R
import com.kavrin.marvin.domain.model.tv.api.detail.EpisodeToAir
import com.kavrin.marvin.ui.theme.EXTRA_SMALL_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.cardContentColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace
import com.kavrin.marvin.util.Constants

@Composable
fun EpisodeToAirItem(
    episodeToAir: EpisodeToAir,
    title: String,
    onEpisodeClicked: (Int?) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable {
                onEpisodeClicked(episodeToAir.id)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(3f),
            verticalArrangement = Arrangement.Center
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${Constants.IMAGE_STILL_BASE_URL}${episodeToAir.stillPath}")
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.episode_pic),
                contentScale = ContentScale.FillWidth
            )
        }

        Spacer(modifier = Modifier.width(SMALL_PADDING))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(7f, fill = true),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "$title Episode:",
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.cardContentColor
            )

            Spacer(modifier = Modifier.height(SMALL_PADDING))

            Text(
                text = "${episodeToAir.name}",
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.cardContentColor
            )

            if (!episodeToAir.overview.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(EXTRA_SMALL_PADDING))

                Text(
                    text = "${episodeToAir.overview}",
                    fontFamily = nunitoTypeFace,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    fontWeight = FontWeight.Normal,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.cardContentColor
                )
            }

            Spacer(modifier = Modifier.height(EXTRA_SMALL_PADDING))

            Text(
                text = "(Season ${episodeToAir.seasonNumber} - Episode ${episodeToAir.episodeNumber})",
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.body2.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.cardContentColor
            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun EpisodeToAirPreview() {
    EpisodeToAirItem(
        episodeToAir = EpisodeToAir(
            airDate = null,
            episodeNumber = 9,
            id = null,
            name = "Midnight Blue",
            overview = "Lorem Ipsum Dolor Sit Amet Lorem Ipsum Dolor Sit Amet Lorem Ipsum Dolor Sit Amet",
            runtime = 54,
            seasonNumber = 1,
            stillPath = null,
            voteAverage = 8.8,
            voteCount = 20
        ),
        title = "Last"
    ) {}
}