package com.kavrin.marvin.navigation

sealed class Screen(val route: String) {

	//// Splash Screen ////
	object Splash : Screen(route = "splash_screen")

	//// OnBoarding Screen ////
	object Welcome : Screen(route = "welcome_screen")

	//// Home Screen ////
	object Home : Screen(route = "home_screen")

	//// Detail Screen ////
	object Detail : Screen(route = "detail_screen/{id}?isMovie={isMovie}") {
		fun passIdAndBool(id: Int, isMovie: Boolean = true): String {
			return "detail_screen/${id}?isMovie=$isMovie"
		}
	}

	//// Search Screen ////
	object Search : Screen(route = "search_screen")

	//// Library Screen ////
	object Library : Screen(route = "library_screen")

	//// List Screen ////
	object List : Screen(route = "search_screen/{listId}") {
		fun passListId(listId: Int): String {
			return "detail_screen/${listId}"
		}
	}

}
