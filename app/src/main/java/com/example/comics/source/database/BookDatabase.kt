package com.example.comics.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.comics.di.ApplicationScope
import com.example.comics.model.Book
import com.example.comics.model.NameConverter
import com.example.comics.model.PriceConverter
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Book::class], version = 1)
@TypeConverters(NameConverter::class, PriceConverter::class)
abstract class BookDatabase : RoomDatabase() {

    abstract fun dao(): Dao

    class Callback @Inject constructor(
        private val database: Provider<BookDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()


}
