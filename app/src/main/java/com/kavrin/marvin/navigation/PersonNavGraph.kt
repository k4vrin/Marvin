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
import com.kavrin.marvin.presentation.screens.person.PersonScreen
import com.kavrin.marvin.util.Constants

fun NavGraphBuilder.personNavGraph(navHostController: NavHostController) {

    navigation(
        startDestination = PersonScreen.Person.route,
        route = Graph.Person.route,
        arguments = listOf(
            navArgument(Constants.ARGUMENT_KEY_ID) {
                type = NavType.IntType
            }
        )
    ) {

        //// Person Screen ////
        composable(
            route = PersonScreen.Person.route,
            arguments = listOf(
                navArgument(Constants.ARGUMENT_KEY_ID) {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(
                        durationMillis = DurationConstants.MEDIUM,
                        delayMillis = DurationConstants.LONG
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    tween(
                        durationMillis = DurationConstants.EXTRA_SHORT,
                        delayMillis = DurationConstants.EXTRA_LONG * 2
                    )
                )
            },
            popEnterTransition = {
                fadeIn(
                    tween(durationMillis = DurationConstants.EXTRA_SHORT)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Down,
                    animationSpec = tween(durationMillis = DurationConstants.SHORT)
                )
            }
        ) {
            PersonScreen(navHostController = navHostController)
        }

    }
}

sealed class PersonScreen(val route: String) {
    object Person : PersonScreen("person_screen/{id}") {
        fun passId(id: Int): String {
            return "person_screen/${id}"
        }
    }
}