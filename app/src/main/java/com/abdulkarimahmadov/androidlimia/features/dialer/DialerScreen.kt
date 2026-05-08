package com.abdulkarimahmadov.androidlimia.features.dialer

import android.content.Intent
import android.net.Uri
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

private val dialpad = listOf(
    listOf("1", "2 ABC", "3 DEF"),
    listOf("4 GHI", "5 JKL", "6 MNO"),
    listOf("7 PQRS", "8 TUV", "9 WXYZ"),
    listOf("*", "0 +", "#")
)

@Composable
fun DialerScreen(viewModel: DialerViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val number by viewModel.numberInput.collectAsStateWithLifecycle()
    val suggestions by viewModel.suggestions.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = number.ifBlank { " " },
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
            textAlign = TextAlign.Start
        )

        if (suggestions.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth().height(120.dp), contentPadding = PaddingValues(horizontal = 20.dp)) {
                items(suggestions, key = { it.phoneNumber }) { item ->
                    Text(
                        text = item.phoneNumber,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .combinedClickable(onClick = { viewModel.append(item.phoneNumber) }, onLongClick = { })
                    )
                }
            }
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceEvenly) {
            dialpad.forEach { row ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    row.forEach { symbol ->
                        DialPadButton(symbol = symbol, onTap = {
                            viewModel.append(symbol.first().toString())
                            vibrate(context)
                        })
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.combinedClickable(onClick = { viewModel.backspace() }, onLongClick = { viewModel.clear() }),
                color = MaterialTheme.colorScheme.surface
            ) {
                Icon(Icons.Default.Backspace, contentDescription = "Delete", modifier = Modifier.padding(12.dp))
            }

            FloatingActionButton(onClick = {
                if (number.isNotBlank()) {
                    viewModel.onDialed()
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
                    context.startActivity(intent)
                }
            }) {
                Icon(Icons.Default.Call, contentDescription = "Call")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DialPadButton(symbol: String, onTap: () -> Unit) {
    Surface(
        modifier = Modifier
            .height(76.dp)
            .combinedClickable(onClick = onTap, onLongClick = onTap),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val chunks = symbol.split(" ")
            Text(chunks.first(), style = MaterialTheme.typography.displayMedium)
            if (chunks.size > 1) {
                Text(chunks.drop(1).joinToString(" "), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

private fun vibrate(context: android.content.Context) {
    val vibrator = context.getSystemService(Vibrator::class.java) ?: return
    vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK))
}
