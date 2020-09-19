package com.rithikjain.projectgists.ui.themes

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
        primary = vividPink,
        primaryVariant = vividPink,
        secondary = lightPurple,
        background = darkPurple,
        onPrimary = Color.White,
)

@Composable
fun EzGistsTheme(content: @Composable () -> Unit) {
  MaterialTheme(
          colors = DarkColorPalette,
          typography = typography,
          shapes = shapes,
          content = content
  )
}