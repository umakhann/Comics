package com.example.comics.source.viewmodel

import androidx.lifecycle.*
import com.example.comics.model.Book
import com.example.comics.model.Comics
import com.example.comics.model.Creator
import com.example.comics.source.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DbViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    companion object {
        var list: MutableLiveData<Comics> = MutableLiveData()
        var creator: MutableLiveData<Creator> = MutableLiveData()
    }

    suspend fun getComics() = repository.getComics().asLiveData()

    fun insert(book: Book) {
        viewModelScope.launch {
            repository.insert(book)
        }
    }

    fun delete(book: Book) {
        viewModelScope.launch {
            repository.delete(book)
        }
    }

    suspend fun rowExists(id: Int) = repository.rowExists(id).asLiveData()
}
