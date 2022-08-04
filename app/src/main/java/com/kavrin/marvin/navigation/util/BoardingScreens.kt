package com.kavrin.marvin.navigation.util

sealed class BoardingScreens(val route: String) {
    //// Splash Screen ////
    object Splash : BoardingScreens(route = "splash_screen")

    //// OnBoarding Screen ////
    object Welcome : BoardingScreens(route = "welcome_screen")
}