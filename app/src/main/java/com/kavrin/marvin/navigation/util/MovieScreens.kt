package com.kavrin.marvin.navigation.util

sealed class MovieScreens(val route: String) {

    //// Movie Screen ////
    object Movie : MovieScreens(route = "movie_screen/{id}") {
        fun passId(id: Int): String {
            return "movie_screen/${id}"
        }
    }
}