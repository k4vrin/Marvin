package com.kavrin.marvin.navigation.util

sealed class TvScreens(val route: String) {

    object Tv : TvScreens("tv_screen/{id}") {
        fun passId(id: Int): String {
            return "tv_screen/${id}"
        }
    }

    object Season : TvScreens("season_screen/{id}/{seasonNumber}") {
        fun passIdAndSeasonNumber(id: Int, seasonNumber: Int): String {
            return "season_screen/${id}/${seasonNumber}"
        }
    }

    object Episode : TvScreens("episode_screen/{id}") {
        fun passId(id: Int): String {
            return "episode_screen/${id}"
        }
    }

}