package com.kavrin.marvin.presentation.screens.tv

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import com.kavrin.marvin.presentation.component.FabAndDivider
import com.kavrin.marvin.presentation.screens.movie.EmptyContent
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
        delay(650)
        uiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }

    val tv by tvViewModel.selectedTv.collectAsState()
    val tvDetailsResultState by tvViewModel.tvDetailsResponse.collectAsStateWithLifecycle()
    val tvRatingsResultState by tvViewModel.tvRatingsResponse.collectAsStateWithLifecycle()
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
                        backdrop = tv?.backdropPath,
                        title = tv?.name,
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
                    tv = tv,
                    tvRuntimeDateStatus = tvStatus,
                    tvGenres = tvGenres,
                    tvRatings = tvRatings,
                    tvCast = tvCast,
                    tvCrew = tvCrew,
                    tvReviews = tvReviews,
                    tvTrailerBackdrop = tvTrailerBackdrop,
                    tvTrailer = tvTrailer,
                    tvVideos = tvVideos,
                    tvSeasons = tvSeasons,
                    toolbarState = collapsingToolbarState,
                    onReviewClicked = {},
                    onVideoClicked = {},
                    onPersonClicked = {},
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