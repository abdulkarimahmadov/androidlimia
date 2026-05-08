package com.abdulkarimahmadov.androidlimia.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorite_contacts ORDER BY displayName COLLATE NOCASE ASC")
    fun observeFavorites(): Flow<List<FavoriteContactEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: FavoriteContactEntity)

    @Query("DELETE FROM favorite_contacts WHERE contactId = :contactId")
    suspend fun delete(contactId: Long)
}

@Dao
interface DialSuggestionsDao {
    @Query("SELECT * FROM dial_suggestions WHERE phoneNumber LIKE :query || '%' ORDER BY usageCount DESC, updatedAtMillis DESC LIMIT 10")
    fun observeSuggestions(query: String): Flow<List<DialSuggestionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: DialSuggestionEntity)

    @Query("DELETE FROM dial_suggestions")
    suspend fun clearAll()
}
