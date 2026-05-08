package com.abdulkarimahmadov.androidlimia.di

import android.content.ContentResolver
import android.content.Context
import androidx.room.Room
import com.abdulkarimahmadov.androidlimia.data.local.AppDatabase
import com.abdulkarimahmadov.androidlimia.data.repository.ContactsRepository
import com.abdulkarimahmadov.androidlimia.data.repository.DialerRepository
import com.abdulkarimahmadov.androidlimia.data.repository.RecentsRepository
import com.abdulkarimahmadov.androidlimia.data.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver = context.contentResolver

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "androidlimia.db").fallbackToDestructiveMigration().build()

    @Provides fun provideSettingsRepository(@ApplicationContext context: Context) = SettingsRepository(context)
    @Provides fun provideContactsRepository(resolver: ContentResolver, db: AppDatabase) = ContactsRepository(resolver, db.favoritesDao())
    @Provides fun provideRecentsRepository(resolver: ContentResolver) = RecentsRepository(resolver)
    @Provides fun provideDialerRepository(db: AppDatabase) = DialerRepository(db.suggestionsDao())
}
