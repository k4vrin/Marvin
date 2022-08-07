package com.kavrin.marvin.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.kavrin.marvin.navigation.util.Durations
import com.kavrin.marvin.navigation.util.Graph
import com.kavrin.marvin.navigation.util.TvScreens
import com.kavrin.marvin.presentation.screens.tv.TvScreen
import com.kavrin.marvin.presentation.screens.tv_season.SeasonViewModel
import com.kavrin.marvin.presentation.screens.tv_season.season.SeasonScreen
import com.kavrin.marvin.util.Constants

fun NavGraphBuilder.tvNavGraph(navHostController: NavHostController) {

    navigation(
        route = Graph.Tv.route,
        startDestination = TvScreens.Tv.route,
        arguments = listOf(
            navArgument(Constants.ARGUMENT_KEY_ID) {
                type = NavType.IntType
            }
        )

    ) {

        //// Tv Screen ////
        composable(
            route = TvScreens.Tv.route,
            arguments = listOf(
                navArgument(Constants.ARGUMENT_KEY_ID) {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(
                        durationMillis = Durations.MEDIUM,
                        delayMillis = Durations.EXTRA_LONG
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    tween(
                        durationMillis = Durations.MEDIUM,
                        delayMillis = Durations.EXTRA_LONG + Durations.EXTRA_SHORT
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
                    towards = AnimatedContentScope.SlideDirection.Down,
                    animationSpec = tween(
                        durationMillis = Durations.MEDIUM
                    )
                )
            }
        ) {
            TvScreen(navHostController = navHostController)
        }


        ///// Season Screen /////
        composable(
            route = TvScreens.Season.route,
            arguments = listOf(
                navArgument(Constants.ARGUMENT_KEY_ID) {
                    type = NavType.IntType
                },
                navArgument(Constants.ARGUMENT_KEY_SEASON_NUMBER) {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = Durations.MEDIUM,
                        delayMillis = Durations.EXTRA_LONG
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    tween(
                        durationMillis = Durations.MEDIUM,
                        delayMillis = Durations.EXTRA_LONG + Durations.EXTRA_SHORT
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
        ) { navBackStackEntry ->
            val parentEntry = remember(navBackStackEntry) {
                navHostController.getBackStackEntry(route = TvScreens.Season.route)
            }
            val viewModel = hiltViewModel<SeasonViewModel>(parentEntry)
            SeasonScreen(
                navHostController = navHostController,
                viewModel = viewModel
            )
        }

        ///// Episode Screen /////
        composable(
            route = TvScreens.Episode.route,
            arguments = listOf(
                navArgument(Constants.ARGUMENT_KEY_ID) {
                    type = NavType.IntType
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
        ) { navBackStackEntry ->
            val parentEntry = remember(navBackStackEntry) {
                navHostController.getBackStackEntry(route = Graph.Tv.route)
            }
            val viewModel = hiltViewModel<SeasonViewModel>(parentEntry)


        }

    }
}