package com.abdulkarimahmadov.androidlimia.features.dialer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulkarimahmadov.androidlimia.data.local.DialSuggestionEntity
import com.abdulkarimahmadov.androidlimia.data.repository.DialerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class DialerViewModel @Inject constructor(
    private val dialerRepository: DialerRepository
) : ViewModel() {

    private val input = MutableStateFlow("")

    val numberInput: StateFlow<String> = input

    val suggestions: StateFlow<List<DialSuggestionEntity>> = input
        .flatMapLatest { dialerRepository.observeSuggestions(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun append(symbol: String) {
        input.value += symbol
    }

    fun setNumber(number: String) {
        input.value = number
    }

    fun backspace() {
        if (input.value.isNotEmpty()) input.value = input.value.dropLast(1)
    }

    fun clear() {
        input.value = ""
    }

    fun onDialed() {
        viewModelScope.launch { dialerRepository.recordDial(input.value) }
    }
}
