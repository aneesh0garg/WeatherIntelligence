package com.example.weather.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather.presentation.home.HomeScreen
import java.util.NavigableMap

@Composable
fun WeatherNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            HomeScreen()
        }
    }
}