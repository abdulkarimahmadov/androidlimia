package com.abdulkarimahmadov.androidlimia.data.repository

import com.abdulkarimahmadov.androidlimia.data.local.DialSuggestionEntity
import com.abdulkarimahmadov.androidlimia.data.local.DialSuggestionsDao
import kotlinx.coroutines.flow.Flow

class DialerRepository(private val dao: DialSuggestionsDao) {
    fun observeSuggestions(prefix: String): Flow<List<DialSuggestionEntity>> = dao.observeSuggestions(prefix)

    suspend fun recordDial(number: String) {
        if (number.isBlank()) return
        val current = dao.getByNumber(number)
        dao.upsert(
            DialSuggestionEntity(
                phoneNumber = number,
                usageCount = (current?.usageCount ?: 0) + 1,
                updatedAtMillis = System.currentTimeMillis()
            )
        )
    }
}
