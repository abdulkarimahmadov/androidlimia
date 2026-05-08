package com.abdulkarimahmadov.androidlimia.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteContactEntity::class, DialSuggestionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun suggestionsDao(): DialSuggestionsDao
}
