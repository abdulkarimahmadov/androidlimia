package com.abdulkarimahmadov.androidlimia.features.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulkarimahmadov.androidlimia.data.repository.ContactsRepository
import com.abdulkarimahmadov.androidlimia.domain.ContactItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val repository: ContactsRepository
) : ViewModel() {
    private val query = MutableStateFlow("")
    val queryText: StateFlow<String> = query

    val contacts: StateFlow<List<ContactItem>> = query
        .flatMapLatest { repository.observeContacts(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun updateQuery(value: String) {
        query.value = value
    }

    fun toggleFavorite(item: ContactItem) {
        viewModelScope.launch { repository.setFavorite(item, !item.isFavorite) }
    }
}
