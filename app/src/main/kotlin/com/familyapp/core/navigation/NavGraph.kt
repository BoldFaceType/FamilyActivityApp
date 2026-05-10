package com.familyapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.familyapp.features.preferences.ui.PreferencesScreen
import com.familyapp.features.suggestions.ui.SuggestionsScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Preferences.route
    ) {
        composable(Screen.Preferences.route) {
            PreferencesScreen(
                onNavigateToSuggestions = {
                    navController.navigate(Screen.Suggestions.route)
                }
            )
        }
        composable(Screen.Suggestions.route) {
            SuggestionsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
