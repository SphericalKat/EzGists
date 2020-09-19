package com.rithikjain.projectgists.ui.themes

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.font
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.unit.sp
import com.rithikjain.projectgists.R

val circularStd = fontFamily(
  font(R.font.circularstd_black, FontWeight.Black),
  font(R.font.circularstd_medium, FontWeight.Medium),
  font(R.font.circularstd_bold, FontWeight.Bold),
  font(R.font.circularstd_book, FontWeight(350))
)

val typography = Typography(
  defaultFontFamily = circularStd
)