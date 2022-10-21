package com.kavrin.marvin.presentation.screens.tv.tv

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import com.kavrin.marvin.domain.model.common.*
import com.kavrin.marvin.domain.model.tv.api.detail.EpisodeToAir
import com.kavrin.marvin.domain.model.tv.api.detail.Season
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.use_cases.tv.TvUseCaseKeys
import com.kavrin.marvin.presentation.component.*
import com.kavrin.marvin.presentation.screens.tv.tv.components.EpisodeToAirCard
import com.kavrin.marvin.presentation.screens.tv.tv.components.SeasonList
import com.kavrin.marvin.presentation.screens.tv.tv.components.TvCardList
import com.kavrin.marvin.ui.theme.*
import me.onebone.toolbar.CollapsingToolbarScaffoldState

@Composable
fun TvContent(
    tvRuntimeDateStatus: Map<String, String?>,
    overviewTotal: Map<String, String?>,
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
    seasonExpanded: Boolean,
    toolbarState: CollapsingToolbarScaffoldState,
    scrollState: ScrollState,
    reviewState: LazyListState,
    castState: LazyListState,
    crewState: LazyListState,
    similarState: LazyListState,
    recommendState: LazyListState,
    videosState: LazyListState,
    ratingAnimationState: MutableTransitionState<RatingState>,
    onPersonClicked: (Int) -> Unit,
    onReviewClicked: (String) -> Unit,
    onVideoClicked: (String) -> Unit,
    onEpisodeClicked: (Int?) -> Unit,
    onSeasonClicked: (Int) -> Unit,
    onTvClicked: (Int) -> Unit,
    onMenuClicked: (Int) -> Unit,
    onSeasonExpand: () -> Unit
) {

    val animRatings by remember {
        derivedStateOf {
            toolbarState.toolbarState.height == toolbarState.toolbarState.minHeight
        }
    }

    LaunchedEffect(key1 = animRatings) {
        if (animRatings)
            ratingAnimationState.targetState = RatingState.End
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backGroundColor)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            Card(
                backgroundColor = MaterialTheme.colors.primaryCardColor,
                shape = RectangleShape
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = SMALL_PADDING)
                ) {

                    if (tvRuntimeDateStatus.isNotEmpty()) {
                        DateTime(
                            modifier = Modifier
                                .fillMaxWidth(0.8f),
                            date = tvRuntimeDateStatus[TvUseCaseKeys.DATE],
                            time = tvRuntimeDateStatus[TvUseCaseKeys.RUNTIME]?.toInt(),
                            status = tvRuntimeDateStatus[TvUseCaseKeys.STATUS]
                        )
                    }

                    if (!overviewTotal[TvUseCaseKeys.OVERVIEW].isNullOrEmpty()) {
                        overviewTotal[TvUseCaseKeys.OVERVIEW]?.let {
                            Divider(
                                modifier = Modifier
                                    .padding(vertical = MEDIUM_PADDING),
                                color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
                            )

                            Overview(overview = it)
                        }
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
                    backgroundColor = MaterialTheme.colors.primaryCardColor,
                    shape = RectangleShape
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = MEDIUM_PADDING)
                    ) {
                        Rating(
                            ratings = tvRatings,
                            ratingState = ratingAnimationState
                        )
                    }
                }
            }
        }

        ///// Cast List /////
        if (!tvCast.isNullOrEmpty()) {
            Card(
                backgroundColor = MaterialTheme.colors.primaryCardColor,
                shape = RectangleShape
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MEDIUM_PADDING)
                ) {
                    CastList(
                        lazyRowState = castState,
                        cast = tvCast,
                        onCastClicked = {
                            onPersonClicked(it)
                        }
                    )

                }
            }
        }

        ///// Crew List /////
        if (!tvCrew.isNullOrEmpty()) {
            Card(
                backgroundColor = MaterialTheme.colors.primaryCardColor,
                shape = RectangleShape
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MEDIUM_PADDING)
                ) {
                    CrewList(
                        lazyRowState = crewState,
                        crew = tvCrew,
                        onCrewClicked = {
                            onPersonClicked(it)
                        }
                    )
                }
            }
        }

        ///// Episode To Air /////
        if (tvEpisodesToAir.isNotEmpty()) {
            EpisodeToAirCard(
                episodes = tvEpisodesToAir,
                totalEpisodes = overviewTotal[TvUseCaseKeys.TOTAL_EPISODES]?.toInt(),
                onEpisodeClicked = {
                    onEpisodeClicked(it)
                }
            )
        }

        ///// Season List /////
        if (!tvSeasons.isNullOrEmpty()) {
            SeasonList(
                seasons = tvSeasons,
                expanded = seasonExpanded,
                onExpand = onSeasonExpand,
                onSeasonClicked = {
                    onSeasonClicked(it)
                }
            )
        }

        ///// Video Section /////
        if (!tvVideos.isNullOrEmpty()) {
            Card(
                backgroundColor = MaterialTheme.colors.primaryCardColor,
                shape = RectangleShape
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MEDIUM_PADDING)
                ) {
                    VideoSection(
                        lazyRowState = videosState,
                        trailer = tvTrailer,
                        videos = tvVideos,
                        trailerBackdrop = tvTrailerBackdrop,
                        onItemClick = {
                            onVideoClicked(it)
                        }
                    )
                }
            }
        }

        ///// Review Section /////
        if (!tvReviews.isNullOrEmpty()) {
            Card(
                backgroundColor = MaterialTheme.colors.primaryCardColor,
                shape = RectangleShape
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MEDIUM_PADDING)
                ) {
                    ReviewList(
                        lazyRowState = reviewState,
                        reviews = tvReviews,
                        onReviewClicked = {
                            onReviewClicked(it)
                        }
                    )
                }
            }
        }

        ///// Similar Section /////
        if (!tvSimilar.isNullOrEmpty()) {
            Card(
                backgroundColor = MaterialTheme.colors.primaryCardColor,
                shape = RectangleShape
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MEDIUM_PADDING)
                ) {
                    TvCardList(
                        lazyListState = similarState,
                        cardListTitle = "Similar",
                        items = tvSimilar,
                        onTvClicked = { onTvClicked(it) },
                        onMenuClicked = { onMenuClicked(it) }
                    )
                }
            }
        }

        ///// Recommended Section /////
        if (!tvRecommended.isNullOrEmpty()) {
            Card(
                backgroundColor = MaterialTheme.colors.primaryCardColor,
                shape = RectangleShape
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MEDIUM_PADDING)
                ) {
                    TvCardList(
                        lazyListState = recommendState,
                        cardListTitle = "Recommendation",
                        items = tvRecommended,
                        onTvClicked = { onTvClicked(it) },
                        onMenuClicked = { onMenuClicked(it) }
                    )
                }
            }
        }
    }

}