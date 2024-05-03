package com.haw.takonappcompose.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object OnBoarding : Screen("onboarding_screen")
    data object Message : Screen("message_screen")
    data object Role : Screen("role_screen")
    data object CreateRole : Screen("create_role_screen")
    data object CreateTask : Screen("create_task_screen")
}
