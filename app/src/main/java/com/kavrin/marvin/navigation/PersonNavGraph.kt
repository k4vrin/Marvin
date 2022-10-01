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
import com.kavrin.marvin.navigation.util.HomeScreens
import com.kavrin.marvin.navigation.util.PersonScreens
import com.kavrin.marvin.presentation.screens.person.PersonScreen
import com.kavrin.marvin.util.Constants

fun NavGraphBuilder.personNavGraph(navHostController: NavHostController) {

    navigation(
        startDestination = PersonScreens.Person.route,
        route = Graph.Person.route,
        arguments = listOf(
            navArgument(Constants.ARGUMENT_KEY_ID) {
                type = NavType.IntType
            }
        )
    ) {

        //// Person Screen ////
        composable(
            route = PersonScreens.Person.route,
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
                when (targetState.destination.route) {
                    HomeScreens.Home.route -> {
                        slideOutOfContainer(
                            towards = AnimatedContentScope.SlideDirection.Down,
                            animationSpec = tween(
                                durationMillis = Durations.MEDIUM,
                                delayMillis = Durations.MEDIUM
                            )
                        )
                    }
                    else -> {
                        slideOutOfContainer(
                            towards = AnimatedContentScope.SlideDirection.Down,
                            animationSpec = tween(
                                durationMillis = Durations.MEDIUM
                            )
                        )
                    }
                }
            }
        ) {
            PersonScreen(navHostController = navHostController)
        }

    }
}