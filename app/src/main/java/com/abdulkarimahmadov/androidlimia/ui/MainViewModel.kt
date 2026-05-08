package com.abdulkarimahmadov.androidlimia.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulkarimahmadov.androidlimia.data.repository.SettingsRepository
import com.abdulkarimahmadov.androidlimia.domain.LumiaAccentColor
import com.abdulkarimahmadov.androidlimia.domain.LumiaThemeMode
import com.abdulkarimahmadov.androidlimia.domain.ThemeSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val settings: StateFlow<ThemeSettings> = settingsRepository.settings.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        ThemeSettings()
    )

    fun updateMode(mode: LumiaThemeMode) = viewModelScope.launch { settingsRepository.updateMode(mode) }
    fun updateAccent(accent: LumiaAccentColor) = viewModelScope.launch { settingsRepository.updateAccent(accent) }
    fun updateVibration(enabled: Boolean) = viewModelScope.launch { settingsRepository.updateVibration(enabled) }
    fun updateFontScale(scale: Float) = viewModelScope.launch { settingsRepository.updateFontScale(scale) }
}
