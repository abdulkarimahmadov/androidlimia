package com.abdulkarimahmadov.androidlimia.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_contacts")
data class FavoriteContactEntity(
    @PrimaryKey val contactId: Long,
    val displayName: String,
    val phoneNumber: String,
    val photoUri: String?
)

@Entity(tableName = "dial_suggestions")
data class DialSuggestionEntity(
    @PrimaryKey val phoneNumber: String,
    val usageCount: Int,
    val updatedAtMillis: Long
)
