package com.kavrin.marvin.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.kavrin.marvin.navigation.util.AuthScreens
import com.kavrin.marvin.navigation.util.Durations
import com.kavrin.marvin.navigation.util.Graph
import com.kavrin.marvin.presentation.screens.auth.login.LoginScreen
import com.kavrin.marvin.presentation.screens.auth.signup.SignupScreen

fun NavGraphBuilder.authNavGraph(navHostController: NavHostController) {

    navigation(
        route = Graph.Auth.route,
        startDestination = AuthScreens.Login.route
    ) {

        //// Login Screen ////
        composable(
            route = AuthScreens.Login.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(
                        durationMillis = Durations.MEDIUM,
                        delayMillis = Durations.MEDIUM
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
            LoginScreen(navController = navHostController)
        }

        //// Signup Screen ////
        composable(
            route = AuthScreens.SignUp.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(
                        durationMillis = Durations.MEDIUM,
                        delayMillis = Durations.MEDIUM
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
            SignupScreen(navController = navHostController)

        }
    }

}