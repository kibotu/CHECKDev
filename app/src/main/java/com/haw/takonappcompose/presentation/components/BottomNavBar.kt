package com.haw.takonappcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.haw.takonappcompose.navigation.Screen
import com.haw.takonappcompose.ui.theme.TakonAppComposeTheme

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val currentDestination by navController.currentBackStackEntryAsState()
    BottomAppBar(
        modifier = modifier
            .background(androidx.compose.ui.graphics.Color.Red)
        ,
    ) {
        listOf(
            BottomNavItem(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                imageVector = Icons.Outlined.Email,
                currentDestination = currentDestination,
                ownDestination = Screen.Message.route
            ) {
                navController.navigate(Screen.Message.route)
            },

            BottomNavItem(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                imageVector = Icons.Outlined.AccountCircle,
                currentDestination = currentDestination,
                ownDestination = Screen.Role.route
            ) {
                navController.navigate(route = Screen.Role.route)
            },

            BottomNavItem(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                imageVector = Icons.Outlined.Build,
                currentDestination = currentDestination,
                ownDestination = "placeholder"
            ) {
            },

            BottomNavItem(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                imageVector = Icons.Outlined.Settings,
                currentDestination = currentDestination,
                ownDestination = Screen.CreateTask.route
            ) {
                navController.navigate(route = Screen.CreateTask.route)
            },
        )
    }
}

@Preview
@Composable
fun BottomNavBarPreview() {
    TakonAppComposeTheme {
        BottomNavBar(navController = rememberNavController())
    }
}
