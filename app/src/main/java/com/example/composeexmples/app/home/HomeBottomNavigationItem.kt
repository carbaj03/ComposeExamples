package com.example.composeexmples.app.home

import androidx.compose.animation.animate
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Box
import androidx.compose.foundation.ContentGravity
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeBottomNavigationItem(
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    selected: Boolean,
    onSelected: () -> Unit,
    animSpec: AnimationSpec<Float>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.selectable(selected = selected, onClick = onSelected),
        gravity = ContentGravity.Center
    ) {
        // Animate the icon/text positions within the item based on selection
        val animationProgress = animate(if (selected) 1f else 0f, animSpec)
        HomeBottomNavItemLayout(
            icon = icon,
            text = text,
            animationProgress = animationProgress
        )
    }
}
