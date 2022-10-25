package com.kavrin.marvin.presentation.screens.movie

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kavrin.marvin.R
import com.kavrin.marvin.domain.use_cases.movie.MovieUseCaseKeys
import com.kavrin.marvin.presentation.component.*
import com.kavrin.marvin.presentation.screens.movie.component.CollectionList
import com.kavrin.marvin.presentation.screens.movie.component.MovieCardList
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.SMALL_PADDING
import com.kavrin.marvin.ui.theme.backGroundColor

@Composable
fun MovieContent(
    movieStates: MovieDetailsState,
    scrollState: ScrollState,
    reviewState: LazyListState,
    castState: LazyListState,
    crewState: LazyListState,
    similarState: LazyListState,
    recommendState: LazyListState,
    videosState: LazyListState,
    collectionState: LazyListState,
    onPersonClicked: (Int) -> Unit,
    onVideoClicked: (String) -> Unit,
    onReviewClicked: (String) -> Unit,
    onMovieClicked: (Int) -> Unit,
    onMenuClicked: (Int) -> Unit
) {

    val animRatings by remember {
        derivedStateOf {
            movieStates.collapsingToolbar.toolbarState.height == movieStates.collapsingToolbar.toolbarState.minHeight
        }
    }

    LaunchedEffect(key1 = animRatings) {
        if (animRatings)
            movieStates.ratingAnimation.targetState = TransitionState.End
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backGroundColor)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
    ) {


        MarvinBgCard(verticalPadding = SMALL_PADDING) {
            /* ///// Date Time Status ///// */
            movieStates.movie?.releaseRuntimeStatus?.let {
                DateTime(
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    date = it[MovieUseCaseKeys.RELEASE_DATE],
                    time = it[MovieUseCaseKeys.RUNTIME]?.toInt(),
                    status = it[MovieUseCaseKeys.STATUS]
                )
            }

            /* ///// Overview ///// */
            movieStates.movie?.overview?.let {
                MarvinDivider()
                Overview(overview = it)
            }

            /* ///// Genres ///// */
            movieStates.movie?.genres?.let {
                MarvinDivider()
                Genres(
                    genres = it,
                    isMovie = true
                )
            }
        }

        /* ///// Ratings ///// */
        if (movieStates.ratings.isNotEmpty()) {
            MarvinBgCard {
                Rating(
                    ratings = movieStates.ratings,
                    transitionState = movieStates.ratingAnimation
                )
            }
        }

        /* ///// Cast ///// */
        movieStates.movie?.cast?.let {
            MarvinBgCard {
                CastList(
                    lazyRowState = castState,
                    cast = it,
                    onCastClicked = { id ->
                        onPersonClicked(id)
                    }
                )
            }
        }


        /* ///// Crew ///// */
        movieStates.movie?.crew?.let {
            MarvinBgCard {
                CrewList(
                    lazyRowState = crewState,
                    crew = it,
                    onCrewClicked = { id ->
                        onPersonClicked(id)
                    }
                )
            }

            /* ///// Trailer and Videos ///// */
            movieStates.movie.videos?.let {
                MarvinBgCard {
                    VideoSection(
                        lazyRowState = videosState,
                        trailer = movieStates.movie.trailer,
                        videos = movieStates.movie.videos,
                        trailerBackdrop = movieStates.movie.trailerBackdrop,
                        onItemClick = {
                            onVideoClicked(it)
                        }
                    )
                }
            }

            /* ///// Reviews ///// */
            movieStates.movie.reviews?.let {
                MarvinBgCard {
                    ReviewList(
                        lazyRowState = reviewState,
                        reviews = it,
                        onReviewClicked = { reviewId ->
                            onReviewClicked(reviewId)
                        }
                    )
                }

                /* ///// Collection ///// */
                movieStates.collectionMovies?.let {
                    CollectionList(
                        lazyListState = collectionState,
                        collectionName = movieStates.collectionName,
                        collectionOverview = movieStates.collectionOverview,
                        collectionBackdrop = movieStates.collectionBackdrop,
                        movies = it,
                        onMovieClicked = { id -> onMovieClicked(id) },
                        onMenuClicked = { id -> onMenuClicked(id) }
                    )
                }


                /* ///// Similars ///// */
                movieStates.movie.similars?.let {
                    MarvinBgCard {
                        MovieCardList(
                            lazyRowState = similarState,
                            cardListTitle = stringResource(R.string.similar),
                            items = it,
                            onMovieClicked = { id -> onMovieClicked(id) },
                            onMenuClicked = { id -> onMenuClicked(id) }
                        )
                    }
                }

                /* ///// Recommend ///// */
                movieStates.movie.recommendations?.let {
                    MarvinBgCard {
                        MovieCardList(
                            lazyRowState = recommendState,
                            cardListTitle = stringResource(R.string.recommendation),
                            items = it,
                            onMovieClicked = { id -> onMovieClicked(id) },
                            onMenuClicked = { id -> onMenuClicked(id) }
                        )
                    }
                }
            }
        }
    }
}