package com.kavrin.marvin.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.kavrin.marvin.presentation.screens.home.HomeScreen
import com.kavrin.marvin.util.Constants


fun NavGraphBuilder.homeNavGraph(
    navHostController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(
        route = Graph.Home.route,
        startDestination = BottomBarScreen.Home.route,
    ) {

        //// Home Screen ////
        composable(
            route = BottomBarScreen.Home.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(durationMillis = 1000, delayMillis = 700)
                )
            },
            exitTransition = {
                fadeOut(tween(durationMillis = 200, delayMillis = 2000))
            },
            popEnterTransition = {
                fadeIn(tween(durationMillis = 100))
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 500, delayMillis = 200)
                )
            }
        ) {
            HomeScreen(navController = navHostController, paddingValues = paddingValues)
        }

        //// Library Screen ////
        composable(
            route = BottomBarScreen.Library.route
        ) {

        }

        //// Search Screen ////
        composable(route = HomeScreen.Search.route) {

        }

        //// List Screen ////
        composable(
            route = HomeScreen.List.route,
            arguments = listOf(navArgument(Constants.LIST_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {

        }

        ///// Movie Graph /////
        movieNavGraph(navHostController)

        ///// Tv Graph /////
        tvNavGraph(navHostController)

        ///// Person Graph /////
        personNavGraph(navHostController)

    }
}

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "home_screen",
        title = "HOME",
        icon = Icons.Default.Home
    )

    object Library : BottomBarScreen(
        route = "library_screen",
        title = "LIBRARY",
        icon = Icons.Default.CollectionsBookmark
    )
}

sealed class HomeScreen(val route: String) {

    //// Search Screen ////
    object Search : HomeScreen(route = "search_screen")

    //// List Screen ////
    object List : HomeScreen(route = "list_screen/{listId}") {
        fun passListId(listId: Int): String {
            return "list_screen/${listId}"
        }
    }
}