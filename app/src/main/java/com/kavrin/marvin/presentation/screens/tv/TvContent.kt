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
import com.kavrin.marvin.domain.model.tv.api.detail.Season
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.presentation.common.CastList
import com.kavrin.marvin.presentation.common.CrewList
import com.kavrin.marvin.presentation.screens.movie.component.*
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.Constants.TV_RUNTIME_KEY
import com.kavrin.marvin.util.Constants.TV_STATUS_KEY
import me.onebone.toolbar.CollapsingToolbarScaffoldState

@Composable
fun TvContent(
    tv: Tv?,
    tvRuntimeDateStatus: Map<String, String?>?,
    tvGenres: List<String>?,
    tvRatings: Map<String, String?>?,
    tvCast: List<Cast>?,
    tvCrew: List<Crew>?,
    tvReviews: List<Review>?,
    tvTrailer: Video?,
    tvTrailerBackdrop: Backdrop?,
    tvVideos: List<Video>?,
    tvSeasons: List<Season>?,
    toolbarState: CollapsingToolbarScaffoldState,
    onPersonClicked: (Int) -> Unit,
    onReviewClicked: (String) -> Unit,
    onVideoClicked: (String) -> Unit
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
        verticalArrangement = Arrangement.spacedBy(LARGE_PADDING)
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
                        if (tvRuntimeDateStatus != null) {
                            DateTime(
                                date = tv.firstAirDate,
                                time = tvRuntimeDateStatus[TV_RUNTIME_KEY]?.toInt(),
                                status = tvRuntimeDateStatus[TV_STATUS_KEY]
                            )
                        }
                    }

                    Divider(
                        modifier = Modifier
                            .padding(vertical = MEDIUM_PADDING),
                        color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
                    )
                    if (tv != null) {
                        Overview(overview = tv.overview)
                    }

                    Divider(
                        modifier = Modifier
                            .padding(vertical = MEDIUM_PADDING),
                        color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
                    )

                    if (tvGenres != null) {
                        Genres(
                            genres = tvGenres,
                            isMovie = false
                        )
                    }
                }
            }
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
                    if (!tvRatings.isNullOrEmpty()) {
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


        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
    }

}