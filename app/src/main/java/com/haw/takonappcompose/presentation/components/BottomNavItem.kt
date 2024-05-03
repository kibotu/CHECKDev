package com.haw.takonappcompose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.haw.takonappcompose.ui.theme.BluePrimary
import com.haw.takonappcompose.ui.theme.TakonAppComposeTheme

@Composable
fun BottomNavItem(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    currentDestination: NavBackStackEntry?,
    ownDestination: String,
    action: () -> Unit,
) {
    IconButton(
        modifier = modifier.drawBehind {
            if (currentDestination?.destination?.route == ownDestination) {
                drawLine(
                    BluePrimary,
                    Offset(0f, -8f),
                    Offset(size.width, -8f),
                    8f
                )
            }
        },
        onClick = { action() }
    ) {
        Image(
            modifier = Modifier.size(32.dp),
            imageVector = imageVector,
            contentDescription = null,
            colorFilter = ColorFilter.tint(BluePrimary)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavItemPreview() {
    TakonAppComposeTheme {
        Column {
            BottomNavItem(
                currentDestination = null,
                ownDestination = "1",
                imageVector = Icons.Outlined.Home
            ) { }
        }
    }
}