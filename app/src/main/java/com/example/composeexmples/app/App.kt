package com.example.composeexmples.app

import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.composeexmples.app.ui.ComposeExmplesTheme

@Composable
fun App(
    navigationViewModel: NavigationViewModel
) {

    ComposeExmplesTheme {
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
                is Screen.Home -> HomeScreen(
                    navigateTo = navigationViewModel::navigateTo,
                    postsRepository = postsRepository
                )
                is Screen.Interests -> InterestsScreen(
                    navigateTo = navigationViewModel::navigateTo,
                    interestsRepository = interestsRepository
                )
                is Screen.Article -> ArticleScreen(
                    postId = screen.postId,
                    postsRepository = postsRepository,
                    onBack = { navigationViewModel.onBack() }
                )
            }
        }
    }
}