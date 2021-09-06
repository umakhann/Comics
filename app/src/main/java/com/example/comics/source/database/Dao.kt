package com.example.comics.source.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.comics.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: Book)

    @Delete
    suspend fun delete(book: Book)

    @Query("select * from books")
    fun getBookmarks() : Flow<List<Book>>

    @Query("SELECT EXISTS(SELECT * FROM books WHERE id = :id)")
    fun rowExists(id: Int): Flow<Boolean>

}