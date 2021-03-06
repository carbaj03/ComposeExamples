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

package com.example.composeexmples.app.home.model

import androidx.compose.runtime.Immutable

@Immutable
data class SnackCollection(
    val id: Long,
    val name: String,
    val snacks: List<Snack>,
    val type: CollectionType = CollectionType.Normal
)

enum class CollectionType { Normal, Highlight }

/**
 * A fake repo
 */
object SnackRepo {
    suspend fun getSnacks(): List<SnackCollection> = snackCollections
    suspend fun getSnack(snackId: Long): Snack = snacks.find { it.id == snackId }!!
    suspend fun getRelated(@Suppress("UNUSED_PARAMETER") snackId: Long): List<SnackCollection> = related
    suspend fun getFilters(): List<Filter> = filters
}

/**
 * Static data
 */

val tastyTreats = SnackCollection(
    id = 1L,
    name = "Android's picks",
    type = CollectionType.Highlight,
    snacks = snacks.subList(0, 13)
)

val popular = SnackCollection(
    id = 2L,
    name = "Popular on Jetsnack",
    snacks = snacks.subList(14, 19)
)

val wfhFavs = tastyTreats.copy(
    id = 3L,
    name = "WFH favourites"
)

val newlyAdded = popular.copy(
    id = 4L,
    name = "Newly Added"
)

val exclusive = tastyTreats.copy(
    id = 5L,
    name = "Only on Jetsnack"
)

val also = tastyTreats.copy(
    id = 6L,
    name = "Customers also bought"
)

val snackCollections = listOf(
    tastyTreats,
    popular,
    wfhFavs,
    newlyAdded,
    exclusive
)

val related = listOf(
    also,
    popular
)
