package com.abdulkarimahmadov.androidlimia.features.recents

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecentsScreen(viewModel: RecentsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val recents by viewModel.recents.collectAsStateWithLifecycle()
    val query by viewModel.queryText.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        OutlinedTextField(
            value = query,
            onValueChange = viewModel::updateQuery,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
            placeholder = { Text("search history") },
            singleLine = true
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(recents, key = { it.id }) { call ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = { },
                            onLongClick = { }
                        )
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(call.name ?: call.phoneNumber, style = MaterialTheme.typography.titleLarge)
                        Text(
                            "${typeLabel(call.type)} • ${formatDate(call.dateMillis)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.65f)
                        )
                    }
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${call.phoneNumber}"))
                        context.startActivity(intent)
                    }) {
                        Icon(Icons.Default.Call, contentDescription = null)
                    }
                }
            }
        }
    }
}

private fun formatDate(ms: Long): String = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(Date(ms))

private fun typeLabel(type: Int): String = when (type) {
    1 -> "incoming"
    2 -> "outgoing"
    3 -> "missed"
    else -> "call"
}
