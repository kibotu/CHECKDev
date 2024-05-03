package com.haw.takonappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.haw.takonappcompose.presentation.screen.CreateRoleScreen
import com.haw.takonappcompose.presentation.screen.CreateTaskScreen
import com.haw.takonappcompose.presentation.screen.MessageScreen
import com.haw.takonappcompose.presentation.screen.OnBoardingScreen
import com.haw.takonappcompose.presentation.screen.RoleScreen
import com.haw.takonappcompose.presentation.screen.SplashScreen

@Composable
fun MainNavGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = "main_route",
        startDestination = Screen.Message.route,
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.OnBoarding.route) {
            OnBoardingScreen(navController = navController)
        }
        composable(route = Screen.Message.route) {
            MessageScreen(navController = navController)
        }
        composable(route = Screen.Role.route) {
            RoleScreen(navController = navController)
        }
        composable(route = Screen.CreateRole.route) {
            CreateRoleScreen(navController = navController)
        }
        composable(route = Screen.CreateTask.route) {
            CreateTaskScreen(navController = navController)
        }
    }
}
