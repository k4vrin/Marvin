package com.kavrin.marvin.presentation.screens.movie

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kavrin.marvin.navigation.Graph
import com.kavrin.marvin.navigation.MovieScreen
import com.kavrin.marvin.presentation.component.EmptyContent
import com.kavrin.marvin.presentation.component.FabAndDivider
import com.kavrin.marvin.util.NetworkResult
import kotlinx.coroutines.delay
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState


@Composable
fun MovieScreen(
    navHostController: NavHostController,
    movieViewModel: MovieViewModel = hiltViewModel()
) {


    ///// Status Bar /////
    val uiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    LaunchedEffect(key1 = true) {
        movieViewModel.getMovieDetails()
        delay(650)
        uiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }

    val context = LocalContext.current

    val movie by movieViewModel.selectedMovie.collectAsState()
    val movieDetailsResultState by movieViewModel.movieDetailsResponse.collectAsState()
    val movieRatingsResultState by movieViewModel.movieRatingResponse.collectAsState()
    val movieCollectionResultState by movieViewModel.movieCollectionRes.collectAsState()
    val movieRuntime by movieViewModel.movieRuntime.collectAsState()
    val movieGenres by movieViewModel.movieGenre.collectAsState()
    val movieRatings by movieViewModel.movieRatings.collectAsState()
    val movieCast by movieViewModel.movieCast.collectAsState()
    val movieCrew by movieViewModel.movieCrew.collectAsState()
    val movieTrailer by movieViewModel.movieTrailer.collectAsState()
    val movieVideos by movieViewModel.movieVideos.collectAsState()
    val trailerBackdrop by movieViewModel.trailerBackdrop.collectAsState()
    val movieReviews by movieViewModel.movieReviews.collectAsState()
    val movieCollectionName by movieViewModel.movieCollectionName.collectAsState()
    val movieCollection by movieViewModel.movieCollection.collectAsState()
    val recommendation by movieViewModel.movieRecommend.collectAsState()
    val similar by movieViewModel.movieSimilar.collectAsState()


    ///// Handle Errors /////
    var isRefreshing by remember { mutableStateOf(false) }
    val result = handleMovieNetworkResult(
        ratings = movieRatingsResultState,
        details = movieDetailsResultState,
        collection = movieCollectionResultState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            movieViewModel.getMovieDetails()
            isRefreshing = false
        }
    )

    ///// Collapsing Toolbar State /////
    val collapsingToolbarState = rememberCollapsingToolbarScaffoldState()
    val scrollState = rememberScrollState()

    if (result) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            CollapsingToolbarScaffold(
                modifier = Modifier
                    .fillMaxSize(),
                state = collapsingToolbarState,
                scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
                toolbar = {
                    MovieToolbar(
                        state = collapsingToolbarState,
                        backdrop = movie?.backdropPath,
                        title = movie?.title,
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
                    movieRatings = movieRatings,
                    movieRuntime = movieRuntime,
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
                    movie = movie,
                    toolbarState = collapsingToolbarState,
                    onPersonClicked = {
                        navHostController.navigate(Graph.Person.passId(it))
                    },
                    onMovieClicked = {
                        navHostController.navigate(MovieScreen.Movie.passId(it))
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
                modifier = Modifier
                    .align(Alignment.TopEnd),
                onFabClicked = { /*TODO*/ }
            )

        }
    }
}

@Composable
private fun handleMovieNetworkResult(
    ratings: NetworkResult,
    details: NetworkResult,
    collection: NetworkResult,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit
): Boolean {

    return when {
        ratings is NetworkResult.Error || details is NetworkResult.Error || collection is NetworkResult.Error -> {
            EmptyContent(
                isLoading = false,
                isError = true,
                isRefreshing = isRefreshing,
                errorMessage = when {
                    ratings is NetworkResult.Error -> ratings.message
                    details is NetworkResult.Error -> details.message
                    else -> collection.message
                },
                onRefresh = onRefresh
            )
            false
        }
        ratings is NetworkResult.Loading || details is NetworkResult.Loading -> {
            EmptyContent(isLoading = true, isError = false)
            false
        }
        ratings is NetworkResult.Success && details is NetworkResult.Success -> true
        else -> false
    }
}