package com.haw.takonappcompose.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.haw.takonappcompose.ui.theme.TakonAppComposeTheme

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier,
    ) {
        // feel free to change the icons and add/remove items :)
        listOf(
            BottomNavItem(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Outlined.Email,
            ) {
                
            },

            BottomNavItem(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Outlined.AccountCircle,
            ) {
                
            },

            BottomNavItem(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Outlined.Build,
            ) {
                
            },

            BottomNavItem(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Outlined.Settings,
            ) {

            }
        )
    }
}

@Preview
@Composable
fun BottomNavBarPreview() {
    TakonAppComposeTheme {
        BottomNavBar()
    }
}

