package com.abdulkarimahmadov.androidlimia.data.repository

import android.content.ContentResolver
import android.provider.ContactsContract
import com.abdulkarimahmadov.androidlimia.data.local.FavoritesDao
import com.abdulkarimahmadov.androidlimia.data.local.FavoriteContactEntity
import com.abdulkarimahmadov.androidlimia.domain.ContactItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ContactsRepository(
    private val resolver: ContentResolver,
    private val favoritesDao: FavoritesDao
) {
    fun observeContacts(query: String): Flow<List<ContactItem>> = combine(
        loadContacts(query),
        favoritesDao.observeFavorites()
    ) { contacts, favs ->
        val favoriteIds = favs.map { it.contactId }.toSet()
        contacts.map { it.copy(isFavorite = favoriteIds.contains(it.id)) }
    }

    suspend fun setFavorite(item: ContactItem, favorite: Boolean) {
        if (favorite) {
            favoritesDao.upsert(FavoriteContactEntity(item.id, item.displayName, item.phoneNumber, item.photoUri))
        } else {
            favoritesDao.delete(item.id)
        }
    }

    private fun loadContacts(query: String): Flow<List<ContactItem>> = flow {
        val result = mutableListOf<ContactItem>()
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI
        )
        val selection = if (query.isBlank()) null else "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ? OR ${ContactsContract.CommonDataKinds.Phone.NUMBER} LIKE ?"
        val args = if (query.isBlank()) null else arrayOf("%$query%", "%$query%")

        resolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            selection,
            args,
            "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} COLLATE NOCASE ASC"
        )?.use { cursor ->
            val idIdx = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIdx = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numIdx = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val photoIdx = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
            while (cursor.moveToNext()) {
                result += ContactItem(
                    id = cursor.getLong(idIdx),
                    displayName = cursor.getString(nameIdx) ?: "Unknown",
                    phoneNumber = cursor.getString(numIdx) ?: "",
                    photoUri = cursor.getString(photoIdx)
                )
            }
        }
        emit(result.distinctBy { it.id to it.phoneNumber })
    }.flowOn(Dispatchers.IO)
}
