package com.kavrin.marvin.navigation.util

sealed class HomeScreens(val route: String) {

    ///// Home Screen /////
    object Home : HomeScreens(route = "home_screen")

    ///// Library Screen /////
    object Library: HomeScreens(route = "library_screen")

    //// Search Screen ////
    object Search : HomeScreens(route = "search_screen")

    //// List Screen ////
    object List : HomeScreens(route = "list_screen/{listName}?isMovie={isMovie}") {
        fun passListName(listName: String, isMovie: Boolean): String {
            return "list_screen/${listName}?isMovie=$isMovie"
        }
    }
}