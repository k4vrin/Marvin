package com.kavrin.marvin.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation

fun NavGraphBuilder.authNavGraph(navHostController: NavHostController) {

    navigation(
        route = Graph.Auth.route,
        startDestination = AuthScreen.Login.route
    ) {
        /*TODO*/
    }

}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")
}