package com.kavrin.marvin.presentation.screens.movie

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kavrin.marvin.domain.use_cases.movie.MovieUseCaseKeys
import com.kavrin.marvin.navigation.util.Durations
import com.kavrin.marvin.navigation.util.Graph
import com.kavrin.marvin.navigation.util.MovieScreens
import com.kavrin.marvin.presentation.component.EmptyContent
import com.kavrin.marvin.presentation.component.FabAndDivider
import com.kavrin.marvin.util.NetworkResult
import kotlinx.coroutines.delay
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy


@Composable
fun MovieScreen(
    navHostController: NavHostController,
    movieViewModel: MovieViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    ///// Status Bar /////
    val uiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    LaunchedEffect(key1 = true) {
        movieViewModel.getMovieDetails()
        delay(
            ((Durations.MEDIUM + Durations.LONG) * 0.9).toLong()
        )
        uiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }


    val movieDetailsResultState by movieViewModel.movieDetailsResponse.collectAsStateWithLifecycle()
//    val movieRatingsResultState by movieViewModel.movieRatingResponse.collectAsStateWithLifecycle()
//    val movieCollectionResultState by movieViewModel.movieCollectionRes.collectAsStateWithLifecycle()
    val movieToolbarInfo by movieViewModel.toolbarInfo.collectAsStateWithLifecycle()
    val movieReleaseRuntimeStatus by movieViewModel.releaseRuntimeStatus.collectAsStateWithLifecycle()
    val movieOverview by movieViewModel.movieOverview.collectAsStateWithLifecycle()
    val movieGenres by movieViewModel.movieGenre.collectAsStateWithLifecycle()
    val movieRatings by movieViewModel.movieRatings.collectAsStateWithLifecycle()
    val movieCast by movieViewModel.movieCast.collectAsStateWithLifecycle()
    val movieCrew by movieViewModel.movieCrew.collectAsStateWithLifecycle()
    val movieTrailer by movieViewModel.movieTrailer.collectAsStateWithLifecycle()
    val movieVideos by movieViewModel.movieVideos.collectAsStateWithLifecycle()
    val trailerBackdrop by movieViewModel.trailerBackdrop.collectAsStateWithLifecycle()
    val movieReviews by movieViewModel.movieReviews.collectAsStateWithLifecycle()
    val movieCollectionName by movieViewModel.movieCollectionInfo.collectAsStateWithLifecycle()
    val movieCollection by movieViewModel.movieCollection.collectAsStateWithLifecycle()
    val recommendation by movieViewModel.movieRecommend.collectAsStateWithLifecycle()
    val similar by movieViewModel.movieSimilar.collectAsStateWithLifecycle()
    val collapsingToolbarState by movieViewModel.collapsingToolbar
    val fabState by movieViewModel.fabState
    val ratingState by movieViewModel.ratingAnimationState


    ///// Handle Errors /////
    var isRefreshing by remember { mutableStateOf(false) }
    val result = handleMovieNetworkResult(
        details = movieDetailsResultState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            movieViewModel.getMovieDetails()
            isRefreshing = false
        }
    )

    val scrollState = rememberScrollState()
    val reviewState = rememberLazyListState()
    val recommendState = rememberLazyListState()
    val similarState = rememberLazyListState()
    val videosState = rememberLazyListState()
    val castState = rememberLazyListState()
    val crewState = rememberLazyListState()
    val collectionState = rememberLazyListState()

    if (result) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            CollapsingToolbarScaffold(
                modifier = Modifier,
                state = collapsingToolbarState,
                scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
                toolbar = {
                    MovieToolbar(
                        state = collapsingToolbarState,
                        backdrop = movieToolbarInfo[MovieUseCaseKeys.BACKDROP],
                        title = movieToolbarInfo[MovieUseCaseKeys.TITLE],
                        subtitle = movieToolbarInfo[MovieUseCaseKeys.DIRECTOR],
                        onBackIconClicked = {
                            navHostController.popBackStack()
                        },
                        onShareClicked = {
                            /*TODO*/
                        }
                    )
                }
            ) {

                MovieContent(
                    movieReleaseRuntimeStatus = movieReleaseRuntimeStatus,
                    movieOverview = movieOverview,
                    movieRatings = movieRatings,
                    movieGenres = movieGenres,
                    movieCast = movieCast,
                    movieCrew = movieCrew,
                    movieTrailer = movieTrailer,
                    movieVideos = movieVideos,
                    trailerBackdrop = trailerBackdrop,
                    reviews = movieReviews,
                    collectionName = movieCollectionName,
                    collection = movieCollection,
                    recommendation = recommendation,
                    scrollState = scrollState,
                    similar = similar,
                    toolbarState = collapsingToolbarState,
                    recommendState = recommendState,
                    similarState = similarState,
                    reviewState = reviewState,
                    castState = castState,
                    crewState = crewState,
                    videosState = videosState,
                    collectionState = collectionState,
                    ratingAnimationState = ratingState,
                    onPersonClicked = {
                        navHostController.navigate(Graph.Person.passId(it))
                    },
                    onMovieClicked = {
                        navHostController.navigate(MovieScreens.Movie.passId(it))
                    },
                    onMenuClicked = {
                        /*TODO*/
                    },
                    onReviewClicked = {
                        val reviewIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                        context.startActivity(reviewIntent)
                    },
                    onVideoClicked = {
                        val ytApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$it"))
                        val ytWeb = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=$it")
                        )
                        try {
                            context.startActivity(ytApp)
                        } catch (e: ActivityNotFoundException) {
                            context.startActivity(ytWeb)
                        }
                    }
                )

            }

            FabAndDivider(
                collapsingToolbarState = collapsingToolbarState,
                fabState = fabState,
                modifier = Modifier
                    .align(Alignment.TopEnd),
                onFabClicked = { /*TODO*/ }
            )

        }
    }
}

@Composable
private fun handleMovieNetworkResult(
    details: NetworkResult,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit
): Boolean {

    return when (details) {
        is NetworkResult.Error -> {
            EmptyContent(
                isLoading = false,
                isError = true,
                isRefreshing = isRefreshing,
                errorMessage = details.message,
                onRefresh = onRefresh
            )
            false
        }
        is NetworkResult.Loading -> {
            EmptyContent(isLoading = true, isError = false)
            false
        }
        is NetworkResult.Success -> true
    }
}