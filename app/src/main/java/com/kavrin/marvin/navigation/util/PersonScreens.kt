package com.kavrin.marvin.navigation.util

sealed class PersonScreens(val route: String) {
    object Person : PersonScreens("person_screen/{id}") {
        fun passId(id: Int): String {
            return "person_screen/${id}"
        }
    }
}