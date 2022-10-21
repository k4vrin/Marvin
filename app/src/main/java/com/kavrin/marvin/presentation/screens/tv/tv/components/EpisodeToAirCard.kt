package com.kavrin.marvin.presentation.screens.tv.tv.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.kavrin.marvin.domain.model.tv.api.detail.EpisodeToAir
import com.kavrin.marvin.domain.use_cases.tv.TvUseCaseKeys
import com.kavrin.marvin.ui.theme.*

@Composable
fun EpisodeToAirCard(
    episodes: Map<String, EpisodeToAir?>,
    totalEpisodes: Int?,
    onEpisodeClicked: (Int?) -> Unit
) {

    Card(
        backgroundColor = MaterialTheme.colors.primaryCardColor,
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = MEDIUM_PADDING)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                val title = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = nunitoTypeFace,
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.cardContentColor
                        )
                    ) {
                        append(text = "Episodes")
                    }

                    if (totalEpisodes != null) {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = nunitoTypeFace,
                                fontSize = MaterialTheme.typography.body1.fontSize,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colors.cardContentColor
                            )
                        ) {
                            append(text = " (Aired: $totalEpisodes)")
                        }
                    }
                }

                Text(
                    modifier = Modifier
                        .padding(bottom = MEDIUM_PADDING),
                    text = title,
                )

            }


            episodes[TvUseCaseKeys.LAST_EPISODES]?.let { episode ->
                EpisodeToAirItem(
                    episodeToAir = episode,
                    title = "Last",
                    onEpisodeClicked = { id ->
                        onEpisodeClicked(id)
                    }
                )
            }

            episodes[TvUseCaseKeys.NEXT_EPISODES]?.let { episode ->

                Divider(
                    modifier = Modifier
                        .padding(vertical = SMALL_PADDING),
                    color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
                )

                EpisodeToAirItem(
                    episodeToAir = episode,
                    title = "Next",
                    onEpisodeClicked = { id ->
                        onEpisodeClicked(id)
                    }
                )
            }
        }
    }

}