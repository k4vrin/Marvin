package com.kavrin.marvin.presentation.screens.tv_season.season

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kavrin.marvin.domain.use_cases.tv_season.GetTvSeason
import com.kavrin.marvin.navigation.util.Durations
import com.kavrin.marvin.navigation.util.TvScreens
import com.kavrin.marvin.presentation.screens.tv.handleTvNetworkResult
import com.kavrin.marvin.presentation.screens.tv_season.SeasonViewModel
import com.kavrin.marvin.ui.theme.statusBarColor
import kotlinx.coroutines.delay
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy

@Composable
fun SeasonScreen(
    navHostController: NavHostController,
    viewModel: SeasonViewModel
) {

    ///// Status Bar /////
    val uiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val color = MaterialTheme.colors.statusBarColor
    LaunchedEffect(key1 = true) {
        viewModel.getSeason()
        delay(Durations.EXTRA_LONG.toLong())
        uiController.setStatusBarColor(
            color = color,
            darkIcons = useDarkIcons
        )
    }

    val networkResult by viewModel.networkResult.collectAsStateWithLifecycle()
    val seasonInfo by viewModel.seasonInfo.collectAsStateWithLifecycle()
    val episodes by viewModel.seasonEpisodes.collectAsStateWithLifecycle()
    val collapsingToolbarState by viewModel.seasonCollapsingToolbar
    val lazyListState = rememberLazyListState()

    ///// Handle Errors /////
    var isRefreshing by remember { mutableStateOf(false) }
    val result = handleTvNetworkResult(
        tvDetails = networkResult,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.getSeason()
            isRefreshing = false
        }
    )

    if (result) {

        CollapsingToolbarScaffold(
            modifier = Modifier
                .statusBarsPadding(),
            state = collapsingToolbarState,
            scrollStrategy = ScrollStrategy.EnterAlways,
            toolbar = {
                SeasonToolbar(
                    title = seasonInfo[GetTvSeason.NAME],
                    subtitle = seasonInfo[GetTvSeason.AIR_DATE],
                    onBackClicked = {
                        navHostController.popBackStack()
                    },
                    onMenuClicked = { /*TODO*/ }
                )
            }
        ) {

            SeasonContent(
                lazyListState = lazyListState,
                episodes = episodes,
                onEpisodeClicked = {
                    navHostController.navigate(TvScreens.Episode.passId(id = it))
                }
            )

        }

    }


}