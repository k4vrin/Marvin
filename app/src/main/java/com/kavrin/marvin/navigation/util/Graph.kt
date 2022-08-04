package com.kavrin.marvin.navigation.util

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