package com.rubens.conectamedicina.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter


val mainBlue = "#43c2ff"

fun getTintedColorFilter(colorHex: String): ColorFilter{
    val color = Color(android.graphics.Color.parseColor(colorHex))
    return ColorFilter.tint(color)
}