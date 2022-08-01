package com.kavrin.marvin.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.kavrin.marvin.presentation.screens.boarding.BoardingViewModel
import com.kavrin.marvin.presentation.screens.boarding.splash.SplashScreen
import com.kavrin.marvin.presentation.screens.boarding.welcome.WelcomeScreen

fun NavGraphBuilder.boardingNavGraph(navHostController: NavHostController) {

    navigation(
        route = Graph.Boarding.route,
        startDestination = BoardingScreens.Splash.route,
    ) {
        //// Splash Screen ////
        composable(
            route = BoardingScreens.Splash.route,
            exitTransition = {
                when (targetState.destination.route) {
                    BoardingScreens.Welcome.route -> {
                        slideOutOfContainer(
                            towards = AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(durationMillis = DurationConstants.SHORT)
                        )
                    }
                    else -> {
                        slideOutOfContainer(
                            towards = AnimatedContentScope.SlideDirection.Up,
                            animationSpec = tween(
                                durationMillis = DurationConstants.MEDIUM,
                                delayMillis = DurationConstants.EXTRA_LONG
                            )
                        )
                    }
                }
            }
        ) { navBackStack ->
            val parentEntry = remember(navBackStack) {
                navHostController.getBackStackEntry(route = Graph.Boarding.route)
            }
            val viewModel = hiltViewModel<BoardingViewModel>(parentEntry)
            SplashScreen(
                navController = navHostController,
                splashViewModel = viewModel
            )
        }

        //// Welcome Screen ////
        composable(
            route = BoardingScreens.Welcome.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = DurationConstants.SHORT)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(
                        durationMillis = DurationConstants.MEDIUM,
                        delayMillis = DurationConstants.EXTRA_LONG
                    )
                )
            }
        ) {navBackStack ->
            val parentEntry = remember(navBackStack) {
                navHostController.getBackStackEntry(route = Graph.Boarding.route)
            }
            val viewModel = hiltViewModel<BoardingViewModel>(parentEntry)
            WelcomeScreen(
                navController = navHostController,
                welcomeViewModel = viewModel
            )
        }
    }
}

sealed class BoardingScreens(val route: String) {
    //// Splash Screen ////
    object Splash : BoardingScreens(route = "splash_screen")

    //// OnBoarding Screen ////
    object Welcome : BoardingScreens(route = "welcome_screen")
}