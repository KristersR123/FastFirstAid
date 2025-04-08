package com.example.firstaidfast_fyp_project

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(userLat: Double, userLon: Double) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "hospitalList") {
        composable("hospitalList") {
            HospitalQueueScreen(userLat = userLat, userLon = userLon, navController = navController)
        }
        composable(
            route = "hospitalDetail/{hospitalName}/{lat}/{lon}",
            arguments = listOf(
                navArgument("hospitalName") { type = NavType.StringType },
                navArgument("lat") { type = NavType.FloatType },
                navArgument("lon") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("hospitalName") ?: ""
            val lat = backStackEntry.arguments?.getFloat("lat")?.toDouble() ?: 0.0
            val lon = backStackEntry.arguments?.getFloat("lon")?.toDouble() ?: 0.0

            HospitalDetailScreen(
                hospitalName = name,
                hospitalLat = lat,
                hospitalLon = lon,
                navController = navController
            )
        }
    }
}