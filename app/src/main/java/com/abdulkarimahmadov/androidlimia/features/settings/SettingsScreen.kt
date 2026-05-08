package com.abdulkarimahmadov.androidlimia.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abdulkarimahmadov.androidlimia.domain.LumiaAccentColor
import com.abdulkarimahmadov.androidlimia.domain.LumiaThemeMode
import com.abdulkarimahmadov.androidlimia.ui.MainViewModel

@Composable
fun SettingsScreen(viewModel: MainViewModel = hiltViewModel()) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
    ) {
        item {
            Text("theme", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(top = 8.dp, bottom = 12.dp))
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                LumiaThemeMode.entries.forEach { mode ->
                    Text(
                        text = mode.name.lowercase(),
                        color = if (settings.mode == mode) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.clickable { viewModel.updateMode(mode) }
                    )
                }
            }
        }
        item {
            Text("accent", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(top = 20.dp, bottom = 12.dp))
        }
        items(LumiaAccentColor.entries) { accent ->
            Text(
                text = accent.name.lowercase(),
                color = if (settings.accent == accent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = 6.dp).clickable { viewModel.updateAccent(accent) }
            )
        }
        item {
            Text("font scale", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(top = 20.dp, bottom = 8.dp))
            Slider(value = settings.fontScale, onValueChange = viewModel::updateFontScale, valueRange = 0.85f..1.4f)
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("vibration")
                Switch(checked = settings.vibrationEnabled, onCheckedChange = viewModel::updateVibration)
            }
        }
    }
}
