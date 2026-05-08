package com.abdulkarimahmadov.androidlimia.data.repository

import android.content.ContentResolver
import android.provider.CallLog
import com.abdulkarimahmadov.androidlimia.domain.CallLogItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RecentsRepository(private val resolver: ContentResolver) {
    fun observeRecents(query: String): Flow<List<CallLogItem>> = flow {
        val result = mutableListOf<CallLogItem>()
        val projection = arrayOf(
            CallLog.Calls._ID,
            CallLog.Calls.NUMBER,
            CallLog.Calls.CACHED_NAME,
            CallLog.Calls.TYPE,
            CallLog.Calls.DATE,
            CallLog.Calls.DURATION
        )
        val selection = if (query.isBlank()) null else "${CallLog.Calls.NUMBER} LIKE ? OR ${CallLog.Calls.CACHED_NAME} LIKE ?"
        val args = if (query.isBlank()) null else arrayOf("%$query%", "%$query%")
        resolver.query(
            CallLog.Calls.CONTENT_URI,
            projection,
            selection,
            args,
            "${CallLog.Calls.DATE} DESC"
        )?.use { cursor ->
            val idIdx = cursor.getColumnIndexOrThrow(CallLog.Calls._ID)
            val numIdx = cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER)
            val nameIdx = cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME)
            val typeIdx = cursor.getColumnIndexOrThrow(CallLog.Calls.TYPE)
            val dateIdx = cursor.getColumnIndexOrThrow(CallLog.Calls.DATE)
            val durIdx = cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION)
            while (cursor.moveToNext()) {
                result += CallLogItem(
                    id = cursor.getLong(idIdx),
                    phoneNumber = cursor.getString(numIdx) ?: "",
                    name = cursor.getString(nameIdx),
                    type = cursor.getInt(typeIdx),
                    dateMillis = cursor.getLong(dateIdx),
                    durationSeconds = cursor.getLong(durIdx)
                )
            }
        }
        emit(result)
    }.flowOn(Dispatchers.IO)
}
