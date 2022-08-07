package com.kavrin.marvin.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.kavrin.marvin.navigation.util.Durations
import com.kavrin.marvin.navigation.util.Graph
import com.kavrin.marvin.navigation.util.HomeScreens
import com.kavrin.marvin.presentation.screens.home.HomeScreen
import com.kavrin.marvin.presentation.screens.list.ListScreen
import com.kavrin.marvin.presentation.screens.search.SearchScreen
import com.kavrin.marvin.util.Constants


fun NavGraphBuilder.homeNavGraph(
    navHostController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(
        route = Graph.Home.route,
        startDestination = HomeScreens.Home.route,
    ) {

        //// Home Screen ////
        composable(
            route = HomeScreens.Home.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(
                        durationMillis = Durations.MEDIUM,
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    tween(
                        durationMillis = Durations.MEDIUM,
                        delayMillis = Durations.EXTRA_LONG
                    )
                )
            },
            popEnterTransition = {
                fadeIn(
                    tween(
                        durationMillis = Durations.SHORT,
                        delayMillis = Durations.EXTRA_SHORT * 2
                    )
                )
            },
            popExitTransition = {
                fadeOut()
            }
        ) {
            HomeScreen(navController = navHostController, paddingValues = paddingValues)
        }

        //// Library Screen ////
        composable(
            route = HomeScreens.Library.route
        ) {

        }

        //// Search Screen ////
        composable(
            route = HomeScreens.Search.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = Durations.MEDIUM,
                        delayMillis = Durations.LONG
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    tween(
                        durationMillis = Durations.MEDIUM,
                        delayMillis = Durations.EXTRA_LONG
                    )
                )
            },
            popEnterTransition = {
                fadeIn(
                    tween(
                        durationMillis = Durations.SHORT,
                        delayMillis = Durations.EXTRA_SHORT * 2
                    )
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = Durations.MEDIUM
                    )
                )
            }

        ) {
            SearchScreen(navHostController = navHostController)
        }

        //// List Screen ////
        composable(
            route = HomeScreens.List.route,
            arguments = listOf(
                navArgument(Constants.LIST_ARGUMENT_KEY_NAME) {
                    type = NavType.StringType
                },
                navArgument(Constants.LIST_ARGUMENT_KEY_IS_MOVIE) {
                    type = NavType.BoolType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = Durations.MEDIUM,
                        delayMillis = Durations.LONG
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    tween(
                        durationMillis = Durations.MEDIUM,
                        delayMillis = Durations.EXTRA_LONG
                    )
                )
            },
            popEnterTransition = {
                fadeIn(
                    tween(
                        durationMillis = Durations.SHORT,
                        delayMillis = Durations.EXTRA_SHORT * 2
                    )
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = Durations.MEDIUM
                    )
                )
            }
        ) {

            ListScreen(navHostController = navHostController)

        }

        ///// Movie Graph /////
        movieNavGraph(navHostController)

        ///// Tv Graph /////
        tvNavGraph(navHostController)

        ///// Person Graph /////
        personNavGraph(navHostController)

    }
}