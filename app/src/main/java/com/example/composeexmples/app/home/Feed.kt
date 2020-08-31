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

import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.composeexmples.app.common.navigationBarsPadding
import com.example.composeexmples.app.common.statusBarsPadding
import com.example.composeexmples.app.components.AppDivider
import com.example.composeexmples.app.components.AppSurface
import com.example.composeexmples.app.components.FilterBar
import com.example.composeexmples.app.home.model.*
import com.example.composeexmples.app.ui.AlphaNearOpaque
import com.example.composeexmples.app.ui.AppTheme

@Composable
fun Feed(
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
//    var data by remember { mutableStateOf(emptyList<SnackCollection>()) }
//    launchInComposition { data = SnackRepo.getSnacks() }
    AppSurface(modifier = modifier.fillMaxSize()) {
        Stack(modifier = Modifier.navigationBarsPadding(left = true, right = true)) {
            SnackCollectionList(snackCollections, filters, onSnackClick)
            DestinationBar()
        }
    }
}

@Composable
private fun SnackCollectionList(
    data: List<SnackCollection>,
    filters: List<Filter>,
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    ScrollableColumn(modifier = modifier) {
        Spacer(
            modifier = Modifier
                .statusBarsPadding()
                .preferredHeight(56.dp)
        )
        FilterBar(filters)
        data.forEachIndexed { index, snackCollection ->
            if (index > 0) {
                AppDivider(thickness = 2.dp)
            }
            key(snackCollection.id) {
                SnackCollection(
                    snackCollection = snackCollection,
                    onSnackClick = onSnackClick,
                    index = index
                )
            }
        }
        Spacer(
            modifier = Modifier
                .navigationBarsPadding(left = false, right = false)
                .preferredHeight(8.dp)
        )
    }
}

@Composable
private fun DestinationBar(modifier: Modifier = Modifier) {
    Column(modifier = modifier.statusBarsPadding()) {
        TopAppBar(
            backgroundColor = AppTheme.colors.uiBackground.copy(alpha = AlphaNearOpaque),
            contentColor = AppTheme.colors.textSecondary,
            elevation = 0.dp
        ) {
            Text(
                text = "Delivery to 1600 Amphitheater Way",
                style = MaterialTheme.typography.subtitle1,
                color = AppTheme.colors.textSecondary,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .gravity(Alignment.CenterVertically)
            )
            IconButton(
                onClick = { /* todo */ },
                modifier = Modifier.gravity(Alignment.CenterVertically)
            ) {
                Icon(
                    asset = Icons.Outlined.ExpandMore,
                    tint = AppTheme.colors.brand
                )
            }
        }
        AppDivider()
    }
}

@Preview("Home")
@Composable
fun HomePreview() {
    AppTheme {
        Feed(onSnackClick = { })
    }
}

@Preview("Home • Dark Theme")
@Composable
fun HomeDarkPreview() {
    AppTheme(darkTheme = true) {
        Feed(onSnackClick = { })
    }
}
