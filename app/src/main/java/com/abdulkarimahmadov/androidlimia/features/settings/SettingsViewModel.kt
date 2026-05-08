package com.abdulkarimahmadov.androidlimia.features.settings

import androidx.lifecycle.ViewModel
import com.abdulkarimahmadov.androidlimia.ui.MainViewModel

class SettingsViewModel(private val mainViewModel: MainViewModel) : ViewModel() {
    val settings = mainViewModel.settings
}
