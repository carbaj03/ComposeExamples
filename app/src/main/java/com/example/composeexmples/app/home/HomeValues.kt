package com.example.composeexmples.app.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.TransformOrigin
import androidx.compose.ui.unit.dp

val BottomNavigationItemPadding: Modifier =
    Modifier.padding(horizontal = 16.dp, vertical = 8.dp)

val BottomNavIndicatorShape: RoundedCornerShape =
    RoundedCornerShape(percent = 50)

val BottomNavLabelTransformOrigin: TransformOrigin =
    TransformOrigin(0f, 0.5f)


val TextIconSpacing = 4.dp
val BottomNavHeight = 56.dp