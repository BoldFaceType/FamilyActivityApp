package com.familyapp.core.navigation

sealed class Screen(val route: String) {
    object Preferences : Screen("preferences")
    object Suggestions : Screen("suggestions")
}
