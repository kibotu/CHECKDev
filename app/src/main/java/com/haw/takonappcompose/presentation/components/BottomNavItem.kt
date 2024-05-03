package com.haw.takonappcompose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haw.takonappcompose.ui.theme.BluePrimary
import com.haw.takonappcompose.ui.theme.TakonAppComposeTheme

@Composable
fun BottomNavItem(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    action: () -> Unit,
) {
    IconButton(
        modifier = modifier,
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
        BottomNavItem(imageVector = Icons.Outlined.Home) { }
    }
}