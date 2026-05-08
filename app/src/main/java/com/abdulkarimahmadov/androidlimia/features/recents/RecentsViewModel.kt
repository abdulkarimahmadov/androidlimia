package com.abdulkarimahmadov.androidlimia.features.recents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulkarimahmadov.androidlimia.data.repository.RecentsRepository
import com.abdulkarimahmadov.androidlimia.domain.CallLogItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class RecentsViewModel @Inject constructor(
    private val repository: RecentsRepository
) : ViewModel() {
    private val query = MutableStateFlow("")
    val queryText: StateFlow<String> = query

    val recents: StateFlow<List<CallLogItem>> = query
        .flatMapLatest { repository.observeRecents(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun updateQuery(value: String) {
        query.value = value
    }
}
