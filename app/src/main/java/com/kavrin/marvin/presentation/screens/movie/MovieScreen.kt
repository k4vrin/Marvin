package com.kavrin.marvin.presentation.screens.movie

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kavrin.marvin.domain.use_cases.movie.MovieUseCaseKeys
import com.kavrin.marvin.navigation.util.Durations
import com.kavrin.marvin.navigation.util.Graph
import com.kavrin.marvin.navigation.util.MovieScreens
import com.kavrin.marvin.presentation.component.EmptyContent
import com.kavrin.marvin.presentation.component.FabAndDivider
import kotlinx.coroutines.delay
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy


@Composable
fun MovieScreen(
    navHostController: NavHostController,
    viewModel: MovieViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val state = viewModel.state


    /* ///// Status Bar ///// */
    val uiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    LaunchedEffect(key1 = true) {
        viewModel.init()
        delay(
            ((Durations.MEDIUM + Durations.LONG) * 0.9).toLong()
        )
        uiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }

    /* ///// Handle Errors ///// */
    var isRefreshing by remember { mutableStateOf(false) }
    val result = handleMovieNetworkResult(
        isLoading = state.isLoading,
        errorMessage = state.error?.asString(),
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.init()
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
    val toolbarScroll = rememberScrollState()


    val toolbarScrollable by remember {
        derivedStateOf {
            when (scrollState.value) {
                0 -> true
                else -> false
            }
        }
    }

    if (result) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            CollapsingToolbarScaffold(
                modifier = Modifier,
                state = viewModel.state.collapsingToolbar,
                scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
                toolbarModifier = Modifier
                    .verticalScroll(
                        state = toolbarScroll,
                        enabled = toolbarScrollable
                    ),
                toolbar = {
                    MovieToolbar(
                        state = state.collapsingToolbar,
                        backdrop = state.movie?.toolbarInfo?.get(MovieUseCaseKeys.BACKDROP),
                        title = state.movie?.toolbarInfo?.get(MovieUseCaseKeys.TITLE),
                        subtitle = state.movie?.toolbarInfo?.get(MovieUseCaseKeys.DIRECTOR),
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
                    movieStates = state,
                    scrollState = scrollState,
                    recommendState = recommendState,
                    similarState = similarState,
                    reviewState = reviewState,
                    castState = castState,
                    crewState = crewState,
                    videosState = videosState,
                    collectionState = collectionState,
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
                collapsingToolbarState = state.collapsingToolbar,
                fabState = state.fabAnimation,
                modifier = Modifier
                    .align(Alignment.TopEnd),
                onFabClicked = { /*TODO*/ }
            )

        }
    }
}

@Composable
private fun handleMovieNetworkResult(
    isLoading: Boolean,
    errorMessage: String?,
    isError: Boolean = errorMessage != null,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit
): Boolean {

    return when  {
        isError -> {
            EmptyContent(
                isLoading = false,
                isError = true,
                isRefreshing = isRefreshing,
                errorMessage = errorMessage,
                onRefresh = onRefresh
            )
            false
        }
        isLoading-> {
            EmptyContent(isLoading = true, isError = false)
            false
        }
        else -> true
    }
}