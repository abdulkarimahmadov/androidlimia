package com.abdulkarimahmadov.androidlimia.data.repository

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.abdulkarimahmadov.androidlimia.domain.LumiaAccentColor
import com.abdulkarimahmadov.androidlimia.domain.LumiaThemeMode
import com.abdulkarimahmadov.androidlimia.domain.ThemeSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "limia_settings")

class SettingsRepository(private val context: Context) {
    private val modeKey = stringPreferencesKey("theme_mode")
    private val accentKey = stringPreferencesKey("accent")
    private val vibrationKey = booleanPreferencesKey("vibration")
    private val fontScaleKey = floatPreferencesKey("font_scale")

    val settings: Flow<ThemeSettings> = context.dataStore.data.map { prefs ->
        ThemeSettings(
            mode = prefs.enum(modeKey, LumiaThemeMode.SYSTEM),
            accent = prefs.enum(accentKey, LumiaAccentColor.CYAN),
            vibrationEnabled = prefs[vibrationKey] ?: true,
            fontScale = prefs[fontScaleKey] ?: 1f
        )
    }

    suspend fun updateMode(mode: LumiaThemeMode) = context.dataStore.edit { it[modeKey] = mode.name }
    suspend fun updateAccent(accent: LumiaAccentColor) = context.dataStore.edit { it[accentKey] = accent.name }
    suspend fun updateVibration(enabled: Boolean) = context.dataStore.edit { it[vibrationKey] = enabled }
    suspend fun updateFontScale(value: Float) = context.dataStore.edit { it[fontScaleKey] = value.coerceIn(0.85f, 1.4f) }

    private inline fun <reified T : Enum<T>> Preferences.enum(key: Preferences.Key<String>, default: T): T {
        return this[key]?.let { runCatching { enumValueOf<T>(it) }.getOrNull() } ?: default
    }
}
