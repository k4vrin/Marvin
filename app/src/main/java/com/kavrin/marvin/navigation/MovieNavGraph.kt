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
import com.kavrin.marvin.presentation.screens.movie.MovieScreen
import com.kavrin.marvin.util.Constants

fun NavGraphBuilder.movieNavGraph(navHostController: NavHostController) {

    navigation(
        startDestination = MovieScreen.Movie.route,
        route = Graph.Movie.route,
        arguments = listOf(
            navArgument(Constants.ARGUMENT_KEY_ID) {
                type = NavType.IntType
            }
        )
    ) {

        //// Movie Screen ////
        composable(
            route = MovieScreen.Movie.route,
            arguments = listOf(
                navArgument(Constants.ARGUMENT_KEY_ID) {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(durationMillis = 500, delayMillis = 150)
                )
            },
            exitTransition = {
                fadeOut(
                    tween(durationMillis = 100, delayMillis = 2000)
                )
            },
            popEnterTransition = {
                fadeIn(
                    tween(durationMillis = 50)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Down,
                    animationSpec = tween(durationMillis = 700)
                )
            }
        ) {
            MovieScreen(navHostController = navHostController)
        }

    }
}

sealed class MovieScreen(val route: String) {

    //// Movie Screen ////
    object Movie : MovieScreen(route = "movie_screen/{id}") {
        fun passId(id: Int): String {
            return "movie_screen/${id}"
        }
    }
}