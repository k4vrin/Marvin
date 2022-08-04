package com.kavrin.marvin.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.kavrin.marvin.navigation.util.Graph

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

