package com.kavrin.marvin.navigation.util

sealed class AuthScreens(val route: String) {
    object Login : AuthScreens(route = "LOGIN")
    object SignUp : AuthScreens(route = "SIGN_UP")
    object Forgot : AuthScreens(route = "FORGOT")
}