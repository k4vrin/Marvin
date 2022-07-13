package com.kavrin.marvin.presentation.screens.movie

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kavrin.marvin.R
import com.kavrin.marvin.domain.model.imdb.IMDbRatingApiResponse
import com.kavrin.marvin.domain.model.movie.api.detail.SingleMovieApiResponse
import com.kavrin.marvin.navigation.Screen
import com.kavrin.marvin.ui.theme.*
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
    SideEffect {
        uiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }

    val context = LocalContext.current

    ///// Get Details and Rating /////
    LaunchedEffect(key1 = true) { movieViewModel.getMovieDetails() }

    val movie by movieViewModel.selectedMovie.collectAsState()
    val movieDetailsResultState by movieViewModel.movieDetails.collectAsState()
    val ratings by movieViewModel.ratingsRes.collectAsState()
    val movieRuntime by movieViewModel.movieRuntime.collectAsState()
    val movieGenres by movieViewModel.movieGenre.collectAsState()
    val movieRatings by movieViewModel.movieRatings.collectAsState()
    val movieCast by movieViewModel.movieCast.collectAsState()
    val movieCrew by movieViewModel.movieCrew.collectAsState()
    val movieTrailer by movieViewModel.movieTrailer.collectAsState()
    val movieVideos by movieViewModel.movieVideos.collectAsState()
    val trailerBackdrop by movieViewModel.trailerBackdrop.collectAsState()

    ///// Handle Errors /////
    var isRefreshing by remember { mutableStateOf(false) }
    val result = handleNetworkResult(
        ratings = ratings,
        movieDetails = movieDetailsResultState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            movieViewModel.getMovieDetails()
            isRefreshing = false
        }
    )

    ///// FAB Animations /////
    val fabState = remember { MutableTransitionState(FabState.Start) }
    LaunchedEffect(key1 = result) {
        if (result) {
            delay(450)
            fabState.targetState = FabState.End
        }
    }
    val transition =
        updateTransition(targetState = fabState, label = stringResource(R.string.fab_animation))

    val animFabTranslateX by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        }, label = stringResource(R.string.fab_translate_x)
    ) { state ->
        when (state.targetState) {
            FabState.Start -> 300f
            FabState.End -> 0f
        }
    }

    val animRotate by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        },
        label = stringResource(R.string.fab_rotate)
    ) { state ->
        when (state.targetState) {
            FabState.Start -> 360f
            FabState.End -> 0f
        }
    }

    ///// Collapsing Toolbar State /////
    val collapsingToolbarState = rememberCollapsingToolbarScaffoldState()

    if (result) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            CollapsingToolbarScaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
                state = collapsingToolbarState,
                scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
                toolbar = {
                    MovieToolbar(
                        state = collapsingToolbarState,
                        backdrop = movie?.backdropPath,
                        title = movie?.title,
                        onBackIconClicked = {
                            navHostController.popBackStack()
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
                    movie = movie,
                    toolbarState = collapsingToolbarState,
                    transitionState = movieViewModel.transition,
                    onTransitionChange = {
                        movieViewModel.updateTransitionState(it)
                    },
                    onCastClicked = {
                        navHostController.navigate(Screen.Person.passId(it))
                    },
                    onCrewClicked = {
                        navHostController.navigate(Screen.Person.passId(it))
                    },
                    onVideoClicked = {
                        val ytApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$it"))
                        val ytWeb = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$it"))
                        try {
                            context.startActivity(ytApp)
                        } catch (e: ActivityNotFoundException) {
                            context.startActivity(ytWeb)
                        }
                    }
                )

            }

            Divider(
                modifier = Modifier
                    .offset(
                        y = with(LocalDensity.current) { collapsingToolbarState.toolbarState.height.toDp() }
                    )
                    .graphicsLayer {
                        alpha = 1f - collapsingToolbarState.toolbarState.progress
                    },
                thickness = 2.dp
            )

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = (collapsingToolbarState.toolbarState.height.toDp() - FAB_OFFSET).roundToPx()
                        )
                    }
                    .graphicsLayer {
                        translationX = animFabTranslateX
                        rotationZ = animRotate
                    }
                    .padding(horizontal = SMALL_PADDING)
                    .size(FAB_SIZE),
                onClick = { },
                backgroundColor = MaterialTheme.colors.fabBgColor,
                contentColor = Color.White
            ) {

                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .requiredSize(ICON_SIZE),
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_icon),
                    tint = MaterialTheme.colors.fabContentColor
                )
            }

        }
    }


}

@Composable
fun handleNetworkResult(
    ratings: NetworkResult<IMDbRatingApiResponse>,
    movieDetails: NetworkResult<SingleMovieApiResponse>,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit
): Boolean {

    return when {
        ratings is NetworkResult.Loading || movieDetails is NetworkResult.Loading -> {
            EmptyContent(isLoading = true, isError = false)
            false
        }
        ratings is NetworkResult.Error || movieDetails is NetworkResult.Error -> {
            EmptyContent(
                isLoading = false,
                isError = true,
                isRefreshing = isRefreshing,
                errorMessage = if (ratings is NetworkResult.Error) ratings.message else movieDetails.message,
                onRefresh = onRefresh
            )
            false
        }
        ratings is NetworkResult.Success && movieDetails is NetworkResult.Success -> true
        else -> false
    }
}


enum class FabState {
    Start,
    End
}