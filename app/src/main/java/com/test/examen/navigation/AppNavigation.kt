package com.test.examen.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.test.examen.features.dollar.presentation.DollarScreen
import com.test.examen.features.movies.presentation.MoviesScreen
import com.test.examen.ui.components.BottomNavItem
import com.test.examen.ui.components.FloatingBottomNavBar

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val items = listOf(
        BottomNavItem(Screen.Movie.route, Screen.Movie.label, Icons.Outlined.Movie),
        BottomNavItem(Screen.Profile.route, Screen.Profile.label, Icons.Outlined.Person),
        BottomNavItem(Screen.Dollar.route, Screen.Dollar.label, Icons.Outlined.AttachMoney)
    )
    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route ?: Screen.Movie.route
            FloatingBottomNavBar(
                items = items,
                currentRoute = currentRoute,
                onItemSelected = { item ->
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Movie.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Movie.route) { MoviesScreen() }
            composable(Screen.Profile.route) { Placeholder("Profile") }
            composable(Screen.Dollar.route) { DollarScreen() }
        }
    }
}

@Composable
private fun Placeholder(name: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(name)
    }
}
