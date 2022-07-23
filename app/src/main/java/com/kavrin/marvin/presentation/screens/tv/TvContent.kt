package com.kavrin.marvin.presentation.screens.tv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.kavrin.marvin.domain.model.common.*
import com.kavrin.marvin.domain.model.tv.api.detail.EpisodeToAir
import com.kavrin.marvin.domain.model.tv.api.detail.Season
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.presentation.component.*
import com.kavrin.marvin.presentation.screens.tv.components.EpisodeToAirCard
import com.kavrin.marvin.presentation.screens.tv.components.SeasonList
import com.kavrin.marvin.presentation.screens.tv.components.TvCardList
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.Constants.TV_RUNTIME_KEY
import com.kavrin.marvin.util.Constants.TV_STATUS_KEY
import com.kavrin.marvin.util.Constants.TV_TOTAL_EPISODE_KEY
import me.onebone.toolbar.CollapsingToolbarScaffoldState

@Composable
fun TvContent(
    tv: Tv?,
    tvRuntimeDateStatus: Map<String, String?>,
    tvGenres: List<String>?,
    tvRatings: Map<String, String?>,
    tvCast: List<Cast>?,
    tvCrew: List<Crew>?,
    tvReviews: List<Review>?,
    tvTrailer: Video?,
    tvTrailerBackdrop: Backdrop?,
    tvVideos: List<Video>?,
    tvSeasons: List<Season>?,
    tvRecommended: List<Tv>?,
    tvSimilar: List<Tv>?,
    tvEpisodesToAir: Map<String, EpisodeToAir?>,
    toolbarState: CollapsingToolbarScaffoldState,
    onPersonClicked: (Int) -> Unit,
    onReviewClicked: (String) -> Unit,
    onVideoClicked: (String) -> Unit,
    onEpisodeClicked: (Int?) -> Unit,
    onSeasonClicked: (Int) -> Unit,
    onTvClicked: (Int) -> Unit,
    onMenuClicked: (Int) -> Unit
) {

    val listState = rememberScrollState()

    val animRatings by remember {
        derivedStateOf {
            toolbarState.toolbarState.height == toolbarState.toolbarState.minHeight
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backGroundColor)
            .verticalScroll(state = listState),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            Card(
                modifier = Modifier
                    .padding(all = EXTRA_SMALL_PADDING)
                    .wrapContentHeight(),
                backgroundColor = MaterialTheme.colors.cardColor
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = SMALL_PADDING)
                ) {

                    if (tv != null) {
                        DateTime(
                            modifier = Modifier
                                .fillMaxWidth(0.8f),
                            date = tv.firstAirDate,
                            time = tvRuntimeDateStatus[TV_RUNTIME_KEY]?.toInt(),
                            status = tvRuntimeDateStatus[TV_STATUS_KEY]
                        )
                    }

                    if (tv != null && tv.overview.isNotBlank()) {
                        Divider(
                            modifier = Modifier
                                .padding(vertical = MEDIUM_PADDING),
                            color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
                        )

                        Overview(overview = tv.overview)
                    }

                    if (!tvGenres.isNullOrEmpty()) {
                        Divider(
                            modifier = Modifier
                                .padding(vertical = MEDIUM_PADDING),
                            color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
                        )

                        Genres(
                            genres = tvGenres,
                            isMovie = false
                        )
                    }
                }
            }

            if (tvRatings.isNotEmpty()) {
                ///// Ratings /////
                Card(
                    modifier = Modifier
                        .padding(all = EXTRA_SMALL_PADDING)
                        .wrapContentHeight(),
                    backgroundColor = MaterialTheme.colors.cardColor
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = MEDIUM_PADDING)
                    ) {
                        Rating(ratings = tvRatings, animate = animRatings)
                    }
                }
            }
        }

        ///// Cast List /////
        if (!tvCast.isNullOrEmpty()) {
            CastList(
                cast = tvCast,
                onCastClicked = {
                    onPersonClicked(it)
                }
            )
        }

        ///// Crew List /////
        if (!tvCrew.isNullOrEmpty()) {
            CrewList(
                crew = tvCrew,
                onCrewClicked = {
                    onPersonClicked(it)
                }
            )
        }

        if (tvEpisodesToAir.isNotEmpty()) {
            EpisodeToAirCard(
                episodes = tvEpisodesToAir,
                totalEpisodes = tvRuntimeDateStatus[TV_TOTAL_EPISODE_KEY]?.toInt(),
                onEpisodeClicked = {
                    onEpisodeClicked(it)
                }
            )
        }

        if (!tvSeasons.isNullOrEmpty()) {
            SeasonList(
                seasons = tvSeasons,
                onSeasonClicked = {
                    onSeasonClicked(it)
                }
            )
        }

        if (!tvVideos.isNullOrEmpty()) {
            VideoSection(
                trailer = tvTrailer,
                videos = tvVideos,
                trailerBackdrop = tvTrailerBackdrop,
                onItemClick = {
                    onVideoClicked(it)
                }
            )
        }

        if (!tvReviews.isNullOrEmpty()) {
            ReviewList(
                reviews = tvReviews,
                onReviewClicked = {
                    onReviewClicked(it)
                }
            )
        }

        if (!tvSimilar.isNullOrEmpty()) {
            TvCardList(
                cardListTitle = "Similar",
                items = tvSimilar,
                onTvClicked = { onTvClicked(it) },
                onMenuClicked = { onMenuClicked(it) }
            )
        }

        if (!tvRecommended.isNullOrEmpty()) {
            TvCardList(
                cardListTitle = "Recommendation",
                items = tvRecommended,
                onTvClicked = { onTvClicked(it) },
                onMenuClicked = { onMenuClicked(it) }
            )
        }


        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
    }

}