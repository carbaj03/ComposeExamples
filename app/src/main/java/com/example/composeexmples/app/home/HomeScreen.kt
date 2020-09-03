/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.composeexmples.app.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.samples.SimpleRelativeToSiblingsInRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Modifier
import com.example.composeexmples.app.Screen
import com.example.composeexmples.app.components.HomeScaffold


@Composable
fun HomeScreen(
    navigateTo: (Screen) -> Unit
) {
    val (currentSection, setCurrentSection) = savedInstanceState { HomeSections.Feed }
    val navItems = HomeSections.values().toList()
    HomeScaffold(
        bottomBar = {
            HomeBottomNav(
                currentSection = currentSection,
                onSectionSelected = setCurrentSection,
                items = navItems
            )
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        Crossfade(currentSection) { section ->
            when (section) {
                HomeSections.Feed -> Feed(
                    onSnackClick = {
                        
                    },
                    modifier = modifier
                )
                HomeSections.Search -> SimpleRelativeToSiblingsInRow()
                HomeSections.Cart -> Text(text = "Cart")
                HomeSections.Profile -> Text(text = "Profile")
            }
        }
    }
}