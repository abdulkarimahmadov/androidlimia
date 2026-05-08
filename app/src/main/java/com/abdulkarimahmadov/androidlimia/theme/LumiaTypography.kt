package com.abdulkarimahmadov.androidlimia.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object LumiaTypography {
    private val base = Typography(
        displayLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light, fontSize = 48.sp),
        displayMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light, fontSize = 36.sp),
        headlineLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light, fontSize = 30.sp),
        titleLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, fontSize = 22.sp),
        bodyLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, fontSize = 18.sp),
        bodyMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, fontSize = 15.sp)
    )

    fun scaled(scale: Float): Typography = Typography(
        displayLarge = base.displayLarge.copy(fontSize = base.displayLarge.fontSize * scale),
        displayMedium = base.displayMedium.copy(fontSize = base.displayMedium.fontSize * scale),
        headlineLarge = base.headlineLarge.copy(fontSize = base.headlineLarge.fontSize * scale),
        titleLarge = base.titleLarge.copy(fontSize = base.titleLarge.fontSize * scale),
        bodyLarge = base.bodyLarge.copy(fontSize = base.bodyLarge.fontSize * scale),
        bodyMedium = base.bodyMedium.copy(fontSize = base.bodyMedium.fontSize * scale)
    )
}
