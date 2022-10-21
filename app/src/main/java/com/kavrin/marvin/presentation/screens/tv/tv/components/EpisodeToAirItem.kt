package com.kavrin.marvin.presentation.screens.tv.tv.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
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
            .clickable {
                onEpisodeClicked(episodeToAir.id)
            },
        verticalAlignment = Alignment.Top
    ) {

        Column(
            modifier = Modifier
                .weight(3f),
            verticalArrangement = Arrangement.Top
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(EXTRA_SMALL_PADDING)),
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
                .weight(7f, fill = true),
        ) {

            val headTitle = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = nunitoTypeFace,
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.cardContentColor
                    )
                ) {
                    append(text = "$title Episode:")
                }

                    withStyle(
                        style = SpanStyle(
                            fontFamily = nunitoTypeFace,
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colors.cardContentColor
                        )
                    ) {
                        append(text = " ${episodeToAir.name}")
                    }
            }

            Text(
                text = headTitle,
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