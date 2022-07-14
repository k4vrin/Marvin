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
import com.kavrin.marvin.domain.model.common.*
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.presentation.common.CastList
import com.kavrin.marvin.presentation.common.CrewList
import com.kavrin.marvin.presentation.screens.movie.component.*
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.Constants.COLLECTION_BACKDROP_KEY
import com.kavrin.marvin.util.Constants.COLLECTION_NAME_KEY
import com.kavrin.marvin.util.Constants.COLLECTION_OVERVIEW_KEY
import me.onebone.toolbar.CollapsingToolbarScaffoldState

@Composable
fun MovieContent(
    movie: Movie?,
    movieRuntime: Int?,
    movieGenres: List<String>?,
    movieRatings: Map<String, String?>?,
    movieCast: List<Cast>?,
    movieCrew: List<Crew>?,
    movieTrailer: Video?,
    movieVideos: List<Video>?,
    trailerBackdrop: Backdrop?,
    reviews: List<Review>?,
    collectionName: Map<String, String?>?,
    collection: List<Movie>?,
    recommendation: List<Movie>?,
    similar: List<Movie>?,
    transitionState: State<TransitionState>,
    toolbarState: CollapsingToolbarScaffoldState,
    onTransitionChange: (Boolean) -> Unit,
    onCastClicked: (Int) -> Unit,
    onCrewClicked: (Int) -> Unit,
    onVideoClicked: (String) -> Unit,
    onReviewClicked: (String) -> Unit,
    onMovieClicked: (Int) -> Unit,
    onMenuClicked: (Int) -> Unit
) {

    val listState = rememberLazyListState()

    val animRatings by remember {
        derivedStateOf {
            toolbarState.toolbarState.height == toolbarState.toolbarState.minHeight
        }
    }

    LaunchedEffect(key1 = animRatings) {
        onTransitionChange(animRatings)
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backGroundColor),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        ///// Date Time Genre Overview /////
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
                        if (movieRuntime != null) {
                            DateTime(
                                date = movie.releaseDate,
                                time = movieRuntime,
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

                    if (movieGenres != null) {
                        Genres(
                            genres = movieGenres,
                            isMovie = true
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
                    if (movieRatings != null) {
                        Rating(ratings = movieRatings, tranState = transitionState)
                    }
                }

            }

        }

        ///// Cast List /////
        item {
            if (!movieCast.isNullOrEmpty()) {
                CastList(
                    cast = movieCast,
                    onCastClicked = {
                        onCastClicked(it)
                    }
                )
            }
        }

        ///// Crew List /////
        item {
            if (!movieCrew.isNullOrEmpty()) {
                CrewList(
                    crew = movieCrew,
                    onCrewClicked = {
                        onCrewClicked(it)
                    }
                )
            }
        }

        item {
            if (!movieVideos.isNullOrEmpty()) {
                VideoSection(
                    trailer = movieTrailer,
                    videos = movieVideos,
                    trailerBackdrop = trailerBackdrop,
                    onItemClick = {
                        onVideoClicked(it)
                    }
                )
            }
        }

        item {
            if (!reviews.isNullOrEmpty()) {
                ReviewList(
                    reviews = reviews,
                    onReviewClicked = {
                        onReviewClicked(it)
                    }
                )
            }
        }

        item {
            if (!collection.isNullOrEmpty() && !collectionName.isNullOrEmpty()) {
                CollectionList(
                    collectionName = collectionName[COLLECTION_NAME_KEY],
                    collectionOverview = collectionName[COLLECTION_OVERVIEW_KEY],
                    collectionBackdrop = collectionName[COLLECTION_BACKDROP_KEY],
                    movies = collection,
                    onMovieClicked = { onMovieClicked(it) },
                    onMenuClicked = { onMenuClicked(it) }
                )
            }
        }

        item {
            if (!similar.isNullOrEmpty()) {
                MovieCardList(
                    cardListTitle = "Similar",
                    items = similar,
                    onMovieClicked = { onMovieClicked(it) },
                    onMenuClicked = { onMenuClicked(it) }
                )
            }
        }

        item {
            if (!recommendation.isNullOrEmpty()) {
                MovieCardList(
                    cardListTitle = "Recommendation",
                    items = recommendation,
                    onMovieClicked = { onMovieClicked(it) },
                    onMenuClicked = { onMenuClicked(it) }
                )
            }
        }

    }
}