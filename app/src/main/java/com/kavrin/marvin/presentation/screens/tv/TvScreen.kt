package com.kavrin.marvin.presentation.screens.tv

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
import com.kavrin.marvin.domain.use_cases.tv.TvUseCaseKeys
import com.kavrin.marvin.navigation.DurationConstants
import com.kavrin.marvin.navigation.Graph
import com.kavrin.marvin.navigation.TvScreen
import com.kavrin.marvin.presentation.component.EmptyContent
import com.kavrin.marvin.presentation.component.FabAndDivider
import com.kavrin.marvin.util.NetworkResult
import kotlinx.coroutines.delay
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun TvScreen(
    navHostController: NavHostController,
    tvViewModel: TvViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    ///// Status Bar /////
    val uiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    LaunchedEffect(key1 = true) {
        tvViewModel.getTvDetails()
        delay(
            ((DurationConstants.MEDIUM + DurationConstants.LONG) * 0.9).toLong()
        )
        uiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }

    val tvDetailsResultState by tvViewModel.tvDetailsResponse.collectAsStateWithLifecycle()
    val tvRatingsResultState by tvViewModel.tvRatingsResponse.collectAsStateWithLifecycle()
    val toolbarInfo by tvViewModel.toolbarInfo.collectAsStateWithLifecycle()
    val overviewTotal by tvViewModel.overviewTotalEpisodes.collectAsStateWithLifecycle()
    val tvStatus by tvViewModel.tvRuntimeStatusDate.collectAsStateWithLifecycle()
    val tvGenres by tvViewModel.tvGenres.collectAsStateWithLifecycle()
    val tvRatings by tvViewModel.tvRatings.collectAsStateWithLifecycle()
    val tvCast by tvViewModel.tvCast.collectAsStateWithLifecycle()
    val tvCrew by tvViewModel.tvCrew.collectAsStateWithLifecycle()
    val tvReviews by tvViewModel.tvReviews.collectAsStateWithLifecycle()
    val tvTrailerBackdrop by tvViewModel.tvTrailerBackdrop.collectAsStateWithLifecycle()
    val tvTrailer by tvViewModel.tvTrailer.collectAsStateWithLifecycle()
    val tvVideos by tvViewModel.tvVideos.collectAsStateWithLifecycle()
    val tvSeasons by tvViewModel.tvSeasons.collectAsStateWithLifecycle()
    val tvEpisodesToAir by tvViewModel.tvEpisodesToAir.collectAsStateWithLifecycle()
    val tvSimilar by tvViewModel.tvSimilar.collectAsStateWithLifecycle()
    val tvRecommended by tvViewModel.tvRecommended.collectAsStateWithLifecycle()

    ///// Handle Errors /////
    var isRefreshing by remember { mutableStateOf(false) }
    val result = handleTvNetworkResult(
        ratings = tvRatingsResultState,
        tvDetails = tvDetailsResultState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            tvViewModel.getTvDetails()
            isRefreshing = false
        }
    )

    val collapsingToolbarState = rememberCollapsingToolbarScaffoldState()
    val scrollState = rememberScrollState()

    val reviewState = rememberLazyListState()
    val recommendState = rememberLazyListState()
    val similarState = rememberLazyListState()
    val videosState = rememberLazyListState()
    val castState = rememberLazyListState()
    val crewState = rememberLazyListState()

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
                    TvToolbar(
                        state = collapsingToolbarState,
                        backdrop = toolbarInfo[TvUseCaseKeys.BACKDROP],
                        title = toolbarInfo[TvUseCaseKeys.TITLE],
                        subtitle = toolbarInfo[TvUseCaseKeys.CREATORS],
                        onBackIconClicked = {
                            navHostController.popBackStack()
                        },
                        onShareClicked = {
                            /*TODO*/
                        }
                    )
                }
            ) {
                TvContent(
                    tvRuntimeDateStatus = tvStatus,
                    overviewTotal = overviewTotal,
                    tvGenres = tvGenres,
                    tvRatings = tvRatings,
                    tvCast = tvCast,
                    tvCrew = tvCrew,
                    tvReviews = tvReviews,
                    tvTrailerBackdrop = tvTrailerBackdrop,
                    tvTrailer = tvTrailer,
                    tvVideos = tvVideos,
                    tvSeasons = tvSeasons,
                    tvEpisodesToAir = tvEpisodesToAir,
                    tvSimilar = tvSimilar,
                    tvRecommended = tvRecommended,
                    toolbarState = collapsingToolbarState,
                    scrollState = scrollState,
                    recommendState = recommendState,
                    similarState = similarState,
                    reviewState = reviewState,
                    castState = castState,
                    crewState = crewState,
                    videosState = videosState,
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
                    },
                    onPersonClicked = {
                        navHostController.navigate(Graph.Person.passId(it))
                    },
                    onSeasonClicked = {
                        /*TODO*/
                    },
                    onEpisodeClicked = {
                        /*TODO*/
                    },
                    onTvClicked = {
                        navHostController.navigate(TvScreen.Tv.passId(it))
                    },
                    onMenuClicked = {
                        /*TODO*/
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
private fun handleTvNetworkResult(
    ratings: NetworkResult,
    tvDetails: NetworkResult,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit
): Boolean {

    return when {
        ratings is NetworkResult.Error || tvDetails is NetworkResult.Error  -> {
            EmptyContent(
                isLoading = false,
                isError = true,
                isRefreshing = isRefreshing,
                errorMessage = when {
                    ratings is NetworkResult.Error -> ratings.message
                    else -> tvDetails.message
                },
                onRefresh = onRefresh
            )
            false
        }
        ratings is NetworkResult.Loading || tvDetails is NetworkResult.Loading -> {
            EmptyContent(isLoading = true, isError = false)
            false
        }
        ratings is NetworkResult.Success && tvDetails is NetworkResult.Success -> true
        else -> false
    }
}