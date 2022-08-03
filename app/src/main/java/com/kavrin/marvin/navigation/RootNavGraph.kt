package com.kavrin.marvin.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost

@Composable
fun SetupNavGraph(navHostController: NavHostController, paddingValues: PaddingValues) {

    AnimatedNavHost(
        navController = navHostController,
        startDestination = Graph.Boarding.route,
        route = Graph.Root.route
    ) {
        boardingNavGraph(navHostController)
//        authNavGraph(navHostController) TODO
        homeNavGraph(navHostController, paddingValues)
    }
}

sealed class Graph(val route: String) {
    object Root : Graph(route = "root")
    object Boarding : Graph(route = "boarding")
    object Auth : Graph(route = "auth")
    object Home : Graph(route = "home")
    object Movie : Graph(route = "movie/{id}") {
        fun passId(id: Int): String {
            return "movie/${id}"
        }
    }

    object Tv : Graph(route = "tv/{id}") {
        fun passId(id: Int): String {
            return "tv/${id}"
        }
    }

    object Person : Graph(route = "person/{id}") {
        fun passId(id: Int): String {
            return "person/${id}"
        }
    }
}

object DurationConstants {

    /**
     * 100
     */
    const val EXTRA_SHORT = 100

    /**
     * 300
     */
    const val SHORT = 300

    /**
     * 500
     */
    const val MEDIUM = 500

    /**
     * 700
     */
    const val LONG = 700

    /**
     * 1000
     */
    const val EXTRA_LONG = 1000

}