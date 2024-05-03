package com.haw.takonappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.haw.takonappcompose.navigation.MainNavGraph
import com.haw.takonappcompose.presentation.components.BottomNavBar
import com.haw.takonappcompose.ui.theme.TakonAppComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TakonAppComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Column {
                        MainNavGraph(
                            modifier = Modifier.weight(1f),
                            navController = navController
                        )
                        BottomNavBar(navController = navController)
                    }
                }
            }
        }
    }
}
