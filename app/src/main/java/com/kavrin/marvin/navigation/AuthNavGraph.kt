package com.kavrin.marvin.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.kavrin.marvin.navigation.util.AuthScreens
import com.kavrin.marvin.navigation.util.Graph

fun NavGraphBuilder.authNavGraph(navHostController: NavHostController) {

    navigation(
        route = Graph.Auth.route,
        startDestination = AuthScreens.Login.route
    ) {
        /*TODO*/
    }

}