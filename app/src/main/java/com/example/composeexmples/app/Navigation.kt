/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.example.composeexmples.app

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.composeexmples.app.common.getMutableStateOf


/**
 * Class defining the screens we have in the app: home, article details and interests
 */
sealed class Screen(val id: Int) {
    companion object {
        operator fun invoke(id: Int) =
            when (id) {
                Home.id -> Home
                Material.id -> Material
                Core.id -> Core
                Foundation.id -> Foundation
                else -> Home
            }
    }

    object Home : Screen(0)
    object Material : Screen(1)
    object Core : Screen(2)
    object Foundation : Screen(3)
}


/**
 * Convert a screen to a bundle that can be stored in [SavedStateHandle]
 */
private fun Screen.toBundle(): Bundle {
    return bundleOf("SCREEN" to id)
}

/**
 * Read a bundle stored by [Screen.toBundle] and return desired screen.
 *
 * @return the parsed [Screen]
 * @throws IllegalArgumentException if the bundle could not be parsed
 */
private fun Bundle.toScreen(): Screen {
    val screenName = Screen(getInt("SCREEN"))
    return when (screenName) {
        Screen.Home -> TODO()
        Screen.Material -> TODO()
        Screen.Core -> TODO()
        Screen.Foundation -> TODO()
    }
}

/**
 * This is expected to be replaced by the navigation component, but for now handle navigation
 * manually.
 *
 * Instantiate this ViewModel at the scope that is fully-responsible for navigation, which in this
 * application is [MainActivity].
 *
 * This app has simplified navigation; the back stack is always [Home] or [Home, dest] and more
 * levels are not allowed. To use a similar pattern with a longer back stack, use a [StateList] to
 * hold the back stack state.
 */
class NavigationViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    /**
     * Hold the current screen in an observable, restored from savedStateHandle after process
     * death.
     *
     * mutableStateOf is an observable similar to LiveData that's designed to be read by compose. It
     * supports observability via property delegate syntax as shown here.
     */
    var currentScreen: Screen by savedStateHandle.getMutableStateOf<Screen>(
        key = "",
        default = Screen.Home,
        save = { it.toBundle() },
        restore = { it.toScreen() }
    )
        private set // limit the writes to only inside this class.

    /**
     * Go back (always to [Home]).
     *
     * Returns true if this call caused user-visible navigation. Will always return false
     * when [currentScreen] is [Home].
     */
    @MainThread
    fun onBack(): Boolean {
        val wasHandled = currentScreen != Screen.Home
        currentScreen = Screen.Home
        return wasHandled
    }

    /**
     * Navigate to requested [Screen].
     *
     * If the requested screen is not [Home], it will always create a back stack with one element:
     * ([Home] -> [screen]). More back entries are not supported in this app.
     */
    @MainThread
    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }
}
