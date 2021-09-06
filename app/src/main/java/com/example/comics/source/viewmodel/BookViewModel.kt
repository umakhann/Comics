package com.example.comics.source.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comics.model.Comics
import com.example.comics.model.Creator
import com.example.comics.source.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    companion object {
        var list: MutableLiveData<Comics> = MutableLiveData()
        var creator: MutableLiveData<Creator> = MutableLiveData()
    }

    fun getComics(offset: Int) : MutableLiveData<Comics> {
        viewModelScope.launch {
            list.value = repository.getComics(offset)
        }

        return list
        }

    fun getCreator(id: Int) : MutableLiveData<Comics> {
        viewModelScope.launch {
            list.value = repository.getCreator(id)
        }

        return list
    }


    }
