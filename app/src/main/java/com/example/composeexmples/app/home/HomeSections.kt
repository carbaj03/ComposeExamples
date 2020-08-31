package com.example.composeexmples.app.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.VectorAsset
import com.example.composeexmples.R

enum class HomeSections(
    @StringRes val title: Int,
    val icon: VectorAsset
) {
    Feed(R.string.home_feed, Outlined.Home),
    Search(R.string.home_search, Outlined.Search),
    Cart(R.string.home_cart, Outlined.ShoppingCart),
    Profile(R.string.home_profile, Outlined.AccountCircle)
}