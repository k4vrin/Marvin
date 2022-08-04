package com.kavrin.marvin.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.kavrin.marvin.navigation.util.Durations
import com.kavrin.marvin.navigation.util.Graph
import com.kavrin.marvin.navigation.util.MovieScreens
import com.kavrin.marvin.presentation.screens.movie.MovieScreen
import com.kavrin.marvin.util.Constants

fun NavGraphBuilder.movieNavGraph(navHostController: NavHostController) {

    navigation(
        route = Graph.Movie.route,
        startDestination = MovieScreens.Movie.route,
        arguments = listOf(
            navArgument(Constants.ARGUMENT_KEY_ID) {
                type = NavType.IntType
            }
        )
    ) {

        //// Movie Screen ////
        composable(
            route = MovieScreens.Movie.route,
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
            MovieScreen(navHostController = navHostController)
        }

    }
}

