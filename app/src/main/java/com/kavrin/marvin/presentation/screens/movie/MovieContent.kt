package com.kavrin.marvin.presentation.screens.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kavrin.marvin.domain.model.imdb.IMDbRatingApiResponse
import com.kavrin.marvin.domain.model.movie.api.detail.SingleMovieApiResponse
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.presentation.screens.movie.component.DateTime
import com.kavrin.marvin.presentation.screens.movie.component.Genres
import com.kavrin.marvin.presentation.screens.movie.component.Overview
import com.kavrin.marvin.presentation.screens.movie.component.Rating
import com.kavrin.marvin.ui.theme.*
import me.onebone.toolbar.CollapsingToolbarScaffoldState

@Composable
fun MovieContent(
    movieDetails: SingleMovieApiResponse?,
    ratings: IMDbRatingApiResponse?,
    movie: Movie?,
    toolbarState: CollapsingToolbarScaffoldState,
    movieViewModel: MovieViewModel = hiltViewModel()
) {

    val listState = rememberLazyListState()

    val animRatings by remember {
        derivedStateOf {
            toolbarState.toolbarState.height == toolbarState.toolbarState.minHeight
        }
    }

    LaunchedEffect(key1 = animRatings) {
        movieViewModel.updateTransitionState(animRatings)
    }

    val tranState = movieViewModel.transition


    LazyColumn(
        state = listState,
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

                    if (movie != null) {
                        if (movieDetails != null) {
                            DateTime(
                                date = movie.releaseDate,
                                time = movieDetails.runtime,
                            )
                        }
                    }

                    Divider(
                        modifier = Modifier
                            .padding(vertical = MEDIUM_PADDING),
                        color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
                    )

                    if (movie != null) {
                        Overview(overview = movie.overview)
                    }

                    Divider(
                        modifier = Modifier
                            .padding(vertical = MEDIUM_PADDING),
                        color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
                    )

                    if (movieDetails != null) {
                        Genres(
                            genres = movieDetails.genres.map {
                                it.name
                            },
                            isMovie = true
                        )
                    }

                }

            }
        }



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
                    if (ratings != null) {
                        Rating(ratings = ratings, tranState = tranState)
                    }
                }

            }

        }
    }



}