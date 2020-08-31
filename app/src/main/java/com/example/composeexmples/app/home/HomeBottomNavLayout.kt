package com.example.composeexmples.app.home

import androidx.compose.animation.AnimatedFloatModel
import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Box
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.remember
import androidx.compose.ui.Layout
import androidx.compose.ui.Modifier
import androidx.compose.ui.TransformOrigin
import androidx.compose.ui.layout.id
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.AnimationClockAmbient
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp

@Composable
fun HomeBottomNavLayout(
    selectedIndex: Int,
    itemCount: Int,
    animSpec: AnimationSpec<Float>,
    indicator: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // Track how "selected" each item is [0, 1]
    val clock = AnimationClockAmbient.current
    val selectionFractions = remember(itemCount) {
        List(itemCount) { i ->
            AnimatedFloatModel(if (i == selectedIndex) 1f else 0f, clock)
        }
    }

    // When selection changes, animate the selection fractions
    onCommit(selectedIndex) {
        selectionFractions.forEachIndexed { index, selectionFraction ->
            val target = if (index == selectedIndex) 1f else 0f
            if (selectionFraction.targetValue != target) {
                selectionFraction.animateTo(target, animSpec)
            }
        }
    }
    // Animate the position of the indicator
    val indicatorLeft = animatedFloat(0f)

    Layout(
        modifier = modifier.preferredHeight(BottomNavHeight),
        children = {
            content()
            Box(Modifier.layoutId("indicator"), children = indicator)
        }
    ) { measurables, constraints ->
        check(itemCount == (measurables.size - 1)) // account for indicator

        // Divide the width into n+1 slots and give the selected item 2 slots
        val unselectedWidth = constraints.maxWidth / (itemCount + 1)
        val selectedWidth = constraints.maxWidth - (itemCount - 1) * unselectedWidth
        val indicatorMeasurable = measurables.first { it.id == "indicator" }

        val itemPlaceables = measurables
            .filterNot { it == indicatorMeasurable }
            .mapIndexed { index, measurable ->
                // Animate item's width based upon the selection amount
                val width = lerp(unselectedWidth, selectedWidth, selectionFractions[index].value)
                measurable.measure(
                    constraints.copy(
                        minWidth = width,
                        maxWidth = width
                    )
                )
            }
        val indicatorPlaceable = indicatorMeasurable.measure(
            constraints.copy(
                minWidth = selectedWidth,
                maxWidth = selectedWidth
            )
        )

        // Animate the indicator position
        val targetIndicatorLeft = selectedIndex * unselectedWidth.toFloat()
        if (indicatorLeft.targetValue != targetIndicatorLeft) {
            indicatorLeft.animateTo(targetIndicatorLeft, animSpec)
        }

        layout(
            width = constraints.maxWidth,
            height = itemPlaceables.maxByOrNull { it.height }?.height ?: 0
        ) {
            indicatorPlaceable.place(x = indicatorLeft.value.toInt(), y = 0)
            var x = 0
            itemPlaceables.forEach { placeable ->
                placeable.place(x = x, y = 0)
                x += placeable.width
            }
        }
    }
}