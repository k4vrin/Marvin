package com.kavrin.marvin.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.kavrin.marvin.presentation.screens.splash.SplashScreen
import com.kavrin.marvin.presentation.screens.welcome.WelcomeScreen

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
                            animationSpec = tween(durationMillis = 500)
                        )
                    }
                    else -> {
                        slideOutOfContainer(
                            towards = AnimatedContentScope.SlideDirection.Up,
                            animationSpec = tween(durationMillis = 1000, delayMillis = 700)
                        )
                    }
                }
            }
        ) {
            SplashScreen(navController = navHostController)
        }

        //// Welcome Screen ////
        composable(
            route = BoardingScreens.Welcome.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(durationMillis = 1000, delayMillis = 700)
                )
            }
        ) {
            WelcomeScreen(navController = navHostController)
        }
    }
}

sealed class BoardingScreens(val route: String) {
    //// Splash Screen ////
    object Splash : BoardingScreens(route = "splash_screen")

    //// OnBoarding Screen ////
    object Welcome : BoardingScreens(route = "welcome_screen")
}