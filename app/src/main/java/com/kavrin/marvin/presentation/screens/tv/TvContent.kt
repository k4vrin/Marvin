package com.kavrin.marvin.presentation.screens.tv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.kavrin.marvin.domain.model.tv.entities.Tv
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
    transitionState: State<TransitionState>,
    toolbarState: CollapsingToolbarScaffoldState,
    onTransitionChange: (Boolean) -> Unit,
) {

    val animRatings by remember {
        derivedStateOf {
            toolbarState.toolbarState.height == toolbarState.toolbarState.minHeight
        }
    }

    LaunchedEffect(key1 = animRatings) {
        onTransitionChange(animRatings)
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backGroundColor),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {

        item {

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
        }

        ///// Ratings /////
        item {
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
                        Rating(ratings = tvRatings, tranState = transitionState)
                    }
                }

            }
        }


    }

}