/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.composeexmples.app.home

import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredHeightIn
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.composeexmples.app.components.AppCard
import com.example.composeexmples.app.components.AppSurface
import com.example.composeexmples.app.components.offsetGradientBackground
import com.example.composeexmples.app.home.model.*
import com.example.composeexmples.app.ui.AppTheme
import dev.chrisbanes.accompanist.coil.CoilImage

private val HighlightCardWidth = 170.dp
private val HighlightCardPadding = 16.dp

// The Cards show a gradient which spans 3 cards and scrolls with parallax.
@Composable
private val gradientWidth
    get() = with(DensityAmbient.current) {
        (3 * (HighlightCardWidth + HighlightCardPadding).toPx())
    }

@Composable
fun SnackCollection(
    snackCollection: SnackCollection,
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    index: Int = 0,
    highlight: Boolean = true
) {
    Column(modifier = modifier) {
        Row(
            verticalGravity = Alignment.CenterVertically,
            modifier = Modifier
                .preferredHeightIn(minHeight = 56.dp)
                .padding(start = 24.dp)
        ) {
            Text(
                text = snackCollection.name,
                style = MaterialTheme.typography.h6,
                color = AppTheme.colors.brand,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { /* todo */ },
                modifier = Modifier.gravity(Alignment.CenterVertically)
            ) {
                Icon(
                    asset = Icons.Outlined.ArrowForward,
                    tint = AppTheme.colors.brand
                )
            }
        }
        if (highlight && snackCollection.type == CollectionType.Highlight) {
            HighlightedSnacks(index, snackCollection.snacks, onSnackClick)
        } else {
            Snacks(snackCollection.snacks, onSnackClick)
        }
    }
}

@Composable
private fun HighlightedSnacks(
    index: Int,
    snacks: List<Snack>,
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val scroll = rememberScrollState(0f)
    val gradient = when (index % 2) {
        0 -> AppTheme.colors.gradient6_1
        else -> AppTheme.colors.gradient6_2
    }
    // The Cards show a gradient which spans 3 cards and scrolls with parallax.
    val gradientWidth = with(DensityAmbient.current) {
        (3 * (HighlightCardWidth + HighlightCardPadding).toPx())
    }
    ScrollableRow(
        scrollState = scroll,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.preferredWidth(24.dp))
        snacks.forEachIndexed { index, snack ->
            HighlightSnackItem(snack, onSnackClick, index, gradient, gradientWidth, scroll.value)
            Spacer(modifier = Modifier.preferredWidth(16.dp))
        }
    }
}

@Composable
private fun Snacks(
    snacks: List<Snack>,
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    ScrollableRow(modifier = modifier) {
        Spacer(modifier = Modifier.preferredWidth(16.dp))
        snacks.forEach { snack ->
            SnackItem(snack, onSnackClick)
            Spacer(modifier = Modifier.preferredWidth(8.dp))
        }
    }
}

@Composable
fun SnackItem(
    snack: Snack,
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    AppSurface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(bottom = 8.dp)
    ) {
        Column(
            horizontalGravity = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(onClick = { onSnackClick(snack.id) })
                .padding(8.dp)
        ) {
            SnackImage(
                imageUrl = snack.imageUrl,
                elevation = 4.dp,
                modifier = Modifier.preferredSize(120.dp)
            )
            Text(
                text = snack.name,
                style = MaterialTheme.typography.subtitle1,
                color = AppTheme.colors.textSecondary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun HighlightSnackItem(
    snack: Snack,
    onSnackClick: (Long) -> Unit,
    index: Int,
    gradient: List<Color>,
    gradientWidth: Float,
    scroll: Float,
    modifier: Modifier = Modifier
) {
    val left = index * with(DensityAmbient.current) {
        (HighlightCardWidth + HighlightCardPadding).toPx()
    }
    AppCard(
        elevation = 4.dp,
        modifier = modifier
            .preferredSize(
                width = 170.dp,
                height = 250.dp
            )
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = { onSnackClick(snack.id) })
                .fillMaxSize()
        ) {
            Stack(
                modifier = Modifier
                    .preferredHeight(160.dp)
                    .fillMaxWidth()
            ) {
                val gradientOffset = left - (scroll / 3f)
                Box(
                    modifier = Modifier
                        .preferredHeight(100.dp)
                        .fillMaxWidth()
                        .offsetGradientBackground(gradient, gradientWidth, gradientOffset)
                )
                SnackImage(
                    imageUrl = snack.imageUrl,
                    modifier = Modifier
                        .preferredSize(120.dp)
                        .gravity(Alignment.BottomCenter)
                )
            }
            Spacer(modifier = Modifier.preferredHeight(8.dp))
            Text(
                text = snack.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.h6,
                color = AppTheme.colors.textSecondary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.preferredHeight(4.dp))
            Text(
                text = snack.tagline,
                style = MaterialTheme.typography.body1,
                color = AppTheme.colors.textHelp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun SnackImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    AppSurface(
        color = Color.LightGray,
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier
    ) {
        CoilImage(
            data = imageUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview("Highlight snack card")
@Composable
fun SnackCardPreview() {
    AppTheme {
        HighlightSnackItem(
            snack = snackMock,
            onSnackClick = { },
            index = 0,
            gradient = AppTheme.colors.gradient6_1,
            gradientWidth = gradientWidth,
            scroll = 0f
        )
    }
}

@Preview("Highlight snack card • Dark Theme")
@Composable
fun SnackCardDarkPreview() {
    AppTheme(darkTheme = true) {
        HighlightSnackItem(
            snack = snackMock,
            onSnackClick = { },
            index = 0,
            gradient = AppTheme.colors.gradient6_1,
            gradientWidth = gradientWidth,
            scroll = 0f
        )
    }
}
