package com.example.composeexmples.app.home

import androidx.annotation.FloatRange
import androidx.compose.foundation.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.util.lerp

@Composable
fun HomeBottomNavItemLayout(
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float
) {
    Layout(
        children = {
            Box(Modifier.layoutId("icon"), children = icon)
            val scale = lerp(0.6f, 1f, animationProgress)
            Box(
                modifier = Modifier
                    .layoutId("text")
                    .drawLayer(
                        alpha = animationProgress,
                        scaleX = scale,
                        scaleY = scale,
                        transformOrigin = BottomNavLabelTransformOrigin
                    ),
                paddingStart = TextIconSpacing,
                children = text
            )
        }
    ) { measurables, constraints ->
        val iconPlaceable = measurables.first { it.id == "icon" }.measure(constraints)
        val textPlaceable = measurables.first { it.id == "text" }.measure(constraints)

        placeTextAndIcon(
            textPlaceable,
            iconPlaceable,
            constraints.maxWidth,
            constraints.maxHeight,
            animationProgress
        )
    }
}

private fun MeasureScope.placeTextAndIcon(
    textPlaceable: Placeable,
    iconPlaceable: Placeable,
    width: Int,
    height: Int,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float
): MeasureScope.MeasureResult {
    val iconY = (height - iconPlaceable.height) / 2
    val textY = (height - textPlaceable.height) / 2

    val textWidth = textPlaceable.width * animationProgress
    val iconX = (width - textWidth - iconPlaceable.width) / 2
    val textX = iconX + iconPlaceable.width

    return layout(width, height) {
        iconPlaceable.place(iconX.toInt(), iconY)
        if (animationProgress != 0f) {
            textPlaceable.place(textX.toInt(), textY)
        }
    }
}
