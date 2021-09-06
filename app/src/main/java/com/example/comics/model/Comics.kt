package com.example.comics.model

data class Comics(val data: Data) {


    data class Data (val results: List<Book>)
}