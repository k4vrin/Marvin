package com.kavrin.marvin.presentation.screens.movie

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
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.use_cases.movie.MovieUseCaseKeys
import com.kavrin.marvin.presentation.component.*
import com.kavrin.marvin.presentation.screens.movie.component.CollectionList
import com.kavrin.marvin.presentation.screens.movie.component.MovieCardList
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.Constants.COLLECTION_BACKDROP_KEY
import com.kavrin.marvin.util.Constants.COLLECTION_NAME_KEY
import com.kavrin.marvin.util.Constants.COLLECTION_OVERVIEW_KEY
import me.onebone.toolbar.CollapsingToolbarScaffoldState

@Composable
fun MovieContent(
    movieOverview: String?,
    movieReleaseRuntimeStatus: Map<String, String?>,
    movieGenres: List<String>?,
    movieRatings: Map<String, String?>,
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
    scrollState: ScrollState,
    toolbarState: CollapsingToolbarScaffoldState,
    reviewState: LazyListState,
    castState: LazyListState,
    crewState: LazyListState,
    similarState: LazyListState,
    recommendState: LazyListState,
    videosState: LazyListState,
    collectionState: LazyListState,
    ratingAnimationState: MutableTransitionState<RatingState>,
    onPersonClicked: (Int) -> Unit,
    onVideoClicked: (String) -> Unit,
    onReviewClicked: (String) -> Unit,
    onMovieClicked: (Int) -> Unit,
    onMenuClicked: (Int) -> Unit
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
            ///// Date Time Genre Overview /////
            Card(
                backgroundColor = MaterialTheme.colors.primaryCardColor,
                shape = RectangleShape
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = SMALL_PADDING)
                ) {

                    if (movieReleaseRuntimeStatus.isNotEmpty()) {
                        DateTime(
                            modifier = Modifier
                                .fillMaxWidth(0.8f),
                            date = movieReleaseRuntimeStatus[MovieUseCaseKeys.RELEASE_DATE],
                            time = movieReleaseRuntimeStatus[MovieUseCaseKeys.RUNTIME]?.toInt(),
                            status = movieReleaseRuntimeStatus[MovieUseCaseKeys.STATUS]
                        )
                    }

                    if (!movieOverview.isNullOrBlank()) {
                        Divider(
                            modifier = Modifier
                                .padding(vertical = MEDIUM_PADDING),
                            color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
                        )

                        Overview(overview = movieOverview)
                    }

                    if (!movieGenres.isNullOrEmpty()) {
                        Divider(
                            modifier = Modifier
                                .padding(vertical = MEDIUM_PADDING),
                            color = MaterialTheme.colors.cardContentColor.copy(alpha = 0.2f),
                        )

                        Genres(
                            genres = movieGenres,
                            isMovie = true
                        )
                    }
                }
            }

            if (movieRatings.isNotEmpty()) {
                ///// Ratings /////
                MarvinBgCard {
                    Rating(
                        ratings = movieRatings,
                        ratingState = ratingAnimationState
                    )
                }
            }
        }

        ///// Cast List /////
        if (!movieCast.isNullOrEmpty()) {
            MarvinBgCard {
                CastList(
                    lazyRowState = castState,
                    cast = movieCast,
                    onCastClicked = {
                        onPersonClicked(it)
                    }
                )
            }
        }


        ///// Crew List /////
        if (!movieCrew.isNullOrEmpty()) {
            MarvinBgCard {
                CrewList(
                    lazyRowState = crewState,
                    crew = movieCrew,
                    onCrewClicked = {
                        onPersonClicked(it)
                    }
                )
            }

            if (!movieVideos.isNullOrEmpty()) {
                MarvinBgCard {
                    VideoSection(
                        lazyRowState = videosState,
                        trailer = movieTrailer,
                        videos = movieVideos,
                        trailerBackdrop = trailerBackdrop,
                        onItemClick = {
                            onVideoClicked(it)
                        }
                    )
                }
            }

            if (!reviews.isNullOrEmpty()) {
                MarvinBgCard {
                    ReviewList(
                        lazyRowState = reviewState,
                        reviews = reviews,
                        onReviewClicked = {
                            onReviewClicked(it)
                        }
                    )
                }

                if (!collection.isNullOrEmpty() && !collectionName.isNullOrEmpty()) {
                    CollectionList(
                        lazyListState = collectionState,
                        collectionName = collectionName[COLLECTION_NAME_KEY],
                        collectionOverview = collectionName[COLLECTION_OVERVIEW_KEY],
                        collectionBackdrop = collectionName[COLLECTION_BACKDROP_KEY],
                        movies = collection,
                        onMovieClicked = { onMovieClicked(it) },
                        onMenuClicked = { onMenuClicked(it) }
                    )
                }


                if (!similar.isNullOrEmpty()) {
                    MarvinBgCard {
                        MovieCardList(
                            lazyRowState = similarState,
                            cardListTitle = "Similar",
                            items = similar,
                            onMovieClicked = { onMovieClicked(it) },
                            onMenuClicked = { onMenuClicked(it) }
                        )
                    }

                }

                if (!recommendation.isNullOrEmpty()) {
                    MarvinBgCard {
                        MovieCardList(
                            lazyRowState = recommendState,
                            cardListTitle = "Recommendation",
                            items = recommendation,
                            onMovieClicked = { onMovieClicked(it) },
                            onMenuClicked = { onMenuClicked(it) }
                        )
                    }
                }
            }
        }

    }
}