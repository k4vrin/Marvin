package com.kavrin.marvin.presentation.screens.tv

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kavrin.marvin.R
import com.kavrin.marvin.domain.model.imdb.IMDbRatingApiResponse
import com.kavrin.marvin.domain.model.tv.api.detail.SingleTvApiResponse
import com.kavrin.marvin.presentation.component.FabAndDivider
import com.kavrin.marvin.presentation.component.FabState
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

    val tv by tvViewModel.selectedTv.collectAsState()
    val tvDetailsResultState by tvViewModel.tvDetailsResponse.collectAsState()
    val tvRatingsResultState by tvViewModel.tvRatingsResponse.collectAsState()

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

    LaunchedEffect(key1 = true) {  }

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
                        onBackIconClicked = {},
                        onShareClicked = {}
                    )
                }
            ) {
                TvContent()
            }

            FabAndDivider(
                collapsingToolbarState = collapsingToolbarState,
                animFabTranslateX = animFabTranslateX,
                animRotate = animRotate,
                modifier = Modifier
                    .align(Alignment.TopEnd)
            )

        }
    }





}



@Composable
private fun handleTvNetworkResult(
    ratings: NetworkResult<IMDbRatingApiResponse>,
    tvDetails: NetworkResult<SingleTvApiResponse>,
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