package com.kavrin.marvin.presentation.screens.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kavrin.marvin.navigation.Graph
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    navHostController: NavHostController,
    listViewModel: ListViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()

    val ui = rememberSystemUiController()
    val color = MaterialTheme.colors.statusBarColor
    val useDarkIcons = MaterialTheme.colors.isLight
    LaunchedEffect(key1 = true) {
        delay(1700)
        ui.setStatusBarColor(color = color, darkIcons = useDarkIcons)
    }

    val title = listViewModel.listTitle
    val isMovie = listViewModel.isMovie
    val isConnected by listViewModel.isConnected.collectAsStateWithLifecycle()
    val items =
        if (isMovie == true) {
            when (title) {
                Constants.POPULAR -> listViewModel.popularMovies.collectAsLazyPagingItems()
                Constants.TOP_RATED -> listViewModel.topRatedMovies.collectAsLazyPagingItems()
                else -> listViewModel.trendingMovies.collectAsLazyPagingItems()
            }
        } else {
            when (title) {
                Constants.POPULAR -> listViewModel.popularTvs.collectAsLazyPagingItems()
                Constants.TOP_RATED -> listViewModel.topRatedTvs.collectAsLazyPagingItems()
                else -> listViewModel.trendingTvs.collectAsLazyPagingItems()
            }
        }

    val scaffoldState = rememberScaffoldState()
    val gridState = rememberLazyGridState()
    val showButton by remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex > 1
        }
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding(),
        scaffoldState = scaffoldState,
        topBar = {
            ListToolbar(
                title = title,
                isConnected = isConnected,
                onBackClicked = {
                    navHostController.popBackStack()
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = showButton,
                enter = slideInVertically(
                    animationSpec = tween(durationMillis = 300),
                    initialOffsetY = { it * 2 }
                ),
                exit = slideOutVertically(
                    animationSpec = tween(durationMillis = 300),
                    targetOffsetY = { it * 2 }
                )
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(all = SMALL_PADDING)
                        .size(FAB_SIZE),
                    onClick = {
                        scope.launch {
                            gridState.scrollToItem(0)
                        }
                    },
                    backgroundColor = MaterialTheme.colors.fabBgColor,
                    contentColor = Color.White
                ) {

                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .requiredSize(ICON_SIZE),
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Go Up Icon",
                        tint = MaterialTheme.colors.fabContentColor
                    )
                }

            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { paddingValues ->
        ListContent(
            items = items,
            isMovie = isMovie,
            gridState = gridState,
            paddingValues = paddingValues,
            onCardClicked = { id ->
                if (isMovie == true)
                    navHostController.navigate(Graph.Movie.passId(id))
                else
                    navHostController.navigate(Graph.Tv.passId(id))
            },
            onMenuClicked = { /*TODO*/ }
        )
    }

}