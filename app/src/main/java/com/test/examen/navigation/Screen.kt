package com.test.examen.navigation

sealed class Screen(val route: String, val label: String) {
    object Movie : Screen("movie", "Movie")
    object Profile : Screen("profile", "Profile")
    object Dollar : Screen("dollar", "Dollar")
}

val BottomScreens = listOf(
    Screen.Movie,
    Screen.Profile,
    Screen.Dollar
)
