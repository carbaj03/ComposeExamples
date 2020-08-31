package com.example.composeexmples.app.home

import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ConfigurationAmbient
import androidx.compose.ui.res.stringResource
import androidx.core.os.ConfigurationCompat
import com.example.composeexmples.app.common.navigationBarsPadding
import com.example.composeexmples.app.components.AppSurface
import com.example.composeexmples.app.ui.AppTheme

@Composable
fun HomeBottomNav(
    currentSection: HomeSections,
    onSectionSelected: (HomeSections) -> Unit,
    items: List<HomeSections>,
    color: Color = AppTheme.colors.iconPrimary,
    contentColor: Color = AppTheme.colors.iconInteractive
) {
    AppSurface(
        color = color,
        contentColor = contentColor
    ) {
        val springSpec = remember {
            SpringSpec<Float>(
                // Determined experimentally
                stiffness = 800f,
                dampingRatio = 0.8f
            )
        }
        HomeBottomNavLayout(
            selectedIndex = currentSection.ordinal,
            itemCount = items.size,
            indicator = { HomeBottomNavIndicator() },
            animSpec = springSpec,
            modifier = Modifier.navigationBarsPadding(left = false, right = false)
        ) {
            items.forEach { section ->
                val selected = section == currentSection
                val tint = androidx.compose.animation.animate(
                    if (selected) {
                        AppTheme.colors.iconInteractive
                    } else {
                        AppTheme.colors.iconInteractiveInactive
                    }
                )

                HomeBottomNavigationItem(
                    icon = {
                        Icon(
                            asset = section.icon,
                            tint = tint
                        )
                    },
                    text = {
                        Text(
                            text = stringResource(section.title)
                                .toUpperCase(ConfigurationCompat.getLocales(ConfigurationAmbient.current).get(0)),
                            color = tint,
                            style = MaterialTheme.typography.button,
                            maxLines = 1
                        )
                    },
                    selected = selected,
                    onSelected = { onSectionSelected(section) },
                    animSpec = springSpec,
                    modifier = BottomNavigationItemPadding.clip(BottomNavIndicatorShape)
                )
            }
        }
    }
}
