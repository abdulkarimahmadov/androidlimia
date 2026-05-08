package com.abdulkarimahmadov.androidlimia.domain

data class ContactItem(
    val id: Long,
    val displayName: String,
    val phoneNumber: String,
    val photoUri: String? = null,
    val isFavorite: Boolean = false
)

data class CallLogItem(
    val id: Long,
    val phoneNumber: String,
    val name: String?,
    val type: Int,
    val dateMillis: Long,
    val durationSeconds: Long
)

enum class LumiaThemeMode { SYSTEM, LIGHT, DARK_AMOLED }

enum class LumiaAccentColor(val hex: Long) {
    CYAN(0xFF00AEEF),
    LIME(0xFF8CC63E),
    ORANGE(0xFFF7941D),
    MAGENTA(0xFFE4007C),
    RED(0xFFED1C24),
    BLUE(0xFF0078D7),
    TEAL(0xFF008272)
}

data class ThemeSettings(
    val mode: LumiaThemeMode = LumiaThemeMode.SYSTEM,
    val accent: LumiaAccentColor = LumiaAccentColor.CYAN,
    val vibrationEnabled: Boolean = true,
    val fontScale: Float = 1f
)
