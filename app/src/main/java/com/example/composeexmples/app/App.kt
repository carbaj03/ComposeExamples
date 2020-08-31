package com.example.composeexmples.app

import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.composeexmples.app.common.ProvideDisplayInsets
import com.example.composeexmples.app.home.HomeScreen
import com.example.composeexmples.app.ui.AppTheme

@Composable
fun App(
    navigationViewModel: NavigationViewModel
) {
    AppTheme {
        AppContent(
            navigationViewModel = navigationViewModel
        )
    }
}

@Composable
private fun AppContent(
    navigationViewModel: NavigationViewModel
) {
    Crossfade(navigationViewModel.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.Home ->
                    ProvideDisplayInsets {
                        HomeScreen(
                            navigateTo = navigationViewModel::navigateTo
                        )
                    }
                Screen.Material -> TODO()
                Screen.Core -> TODO()
                Screen.Foundation -> TODO()
            }
        }
    }
}