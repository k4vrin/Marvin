package com.kavrin.marvin.navigation

sealed class Screen(val route: String) {

	//// Splash Screen ////
	object Splash : Screen(route = "splash_screen")

	//// OnBoarding Screen ////
	object Welcome : Screen(route = "welcome_screen")

	//// Home Screen ////
	object Home : Screen(route = "home_screen")

	///// Movie Screen /////
	object Movie : Screen(route = "movie_screen/{id}") {
		fun passId(id: Int): String {
			return "movie_screen/${id}"
		}
	}

	///// Tv Screen /////
	object Tv : Screen(route = "tv_screen/{id}") {
		fun passId(id: Int): String {
			return "tv_screen/${id}"
		}
	}

	///// Person Screen /////
	object Person : Screen(route = "person_screen/{id}") {
		fun passId(id: Int): String {
			return "person_screen/${id}"
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
