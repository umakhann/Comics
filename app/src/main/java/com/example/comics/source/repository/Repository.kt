package com.example.comics.source.repository

import androidx.lifecycle.LiveData
import com.example.comics.model.Book
import com.example.comics.model.Comics
import com.example.comics.model.Creator
import com.example.comics.source.api.Api
import com.example.comics.source.database.Dao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Api,
    private val dao: Dao
) {

    // From Internet
    suspend fun getComics(offset: Int) : Comics {
        return api.getComics(offset)
    }

    suspend fun getCreator(id: Int) : Comics {
        return api.getCreator(id)
    }



    // From Database
    suspend fun getComics() : Flow<List<Book>> {
        return dao.getBookmarks()
    }

    suspend fun insert(book: Book){
        dao.insert(book)
    }

    suspend fun delete(book: Book){
        dao.delete(book)
    }

    suspend fun rowExists(id: Int) : Flow<Boolean> {
        return dao.rowExists(id)
    }



}