package com.kavrin.marvin.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.kavrin.marvin.presentation.screens.detail.DetailScreen
import com.kavrin.marvin.presentation.screens.home.HomeScreen
import com.kavrin.marvin.presentation.screens.splash.SplashScreen
import com.kavrin.marvin.presentation.screens.welcome.WelcomeScreen
import com.kavrin.marvin.util.Constants.DETAILS_ARGUMENT_KEY_BOOL
import com.kavrin.marvin.util.Constants.DETAILS_ARGUMENT_KEY_ID
import com.kavrin.marvin.util.Constants.LIST_ARGUMENT_KEY

@Composable
fun SetupNavGraph(navHostController: NavHostController) {

	AnimatedNavHost(
		navController = navHostController,
		startDestination = Screen.Splash.route
	) {

		//// Splash Screen ////
		composable(
			route = Screen.Splash.route,
			exitTransition = {
				when (targetState.destination.route) {
					Screen.Welcome.route -> {
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

		//// OnBoarding Screen ////
		composable(
			route = Screen.Welcome.route,
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

		//// Home Screen ////
		composable(
			route = Screen.Home.route,
			enterTransition = {
				slideIntoContainer(
					towards = AnimatedContentScope.SlideDirection.Up,
					animationSpec = tween(durationMillis = 1000, delayMillis = 700)
				)
			},
			exitTransition = {
				fadeOut(
					tween(durationMillis = 2000)
				)
			},
			popEnterTransition = {
				fadeIn(
					tween(durationMillis = 500)
				)
			}
		) {
			HomeScreen(navController = navHostController)
		}

		//// Detail Screen ////
		composable(
			route = Screen.Detail.route,
			arguments = listOf(
				navArgument(DETAILS_ARGUMENT_KEY_ID) {
				type = NavType.IntType
			},
				navArgument(DETAILS_ARGUMENT_KEY_BOOL) {
					type = NavType.BoolType
				}
			),
			enterTransition = {
				expandVertically(
					animationSpec = tween(durationMillis = 500, delayMillis = 200),
					expandFrom = Alignment.Top,
					clip = true
				)
			},
			exitTransition = {
				slideOutOfContainer(
					towards = AnimatedContentScope.SlideDirection.Down,
					animationSpec = tween(durationMillis = 500)
				)
			},
			popExitTransition = {
				slideOutOfContainer(
					towards = AnimatedContentScope.SlideDirection.Down,
					animationSpec = tween(durationMillis = 500)
				)
			}
		) {
			DetailScreen(navHostController = navHostController)

		}

		//// Search Screen ////
		composable(route = Screen.Search.route) {

		}

		//// Library Screen ////
		composable(route = Screen.Library.route) {

		}

		//// List Screen ////
		composable(
			route = Screen.List.route,
			arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
				type = NavType.IntType
			})
		) {

		}


	}
}