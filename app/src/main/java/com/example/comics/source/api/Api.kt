package com.example.comics.source.api

import com.example.comics.BuildConfig
import com.example.comics.adapter.Click
import com.example.comics.model.Comics
import com.example.comics.model.Creator
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    companion object{
        const val ts = BuildConfig.TS
        const val apikey = BuildConfig.API_KEY
        const val hash = BuildConfig.HASH
    }

    @GET("v1/public/comics?format=comic&formatType=comic&ts=$ts&apikey=$apikey&hash=$hash")
    suspend fun getComics(@Query("offset") offset: Int) : Comics



    @GET("v1/public/creators/{id}/comics?ts=$ts&apikey=$apikey&hash=$hash")
    suspend fun getCreator(@Path("id") id : Int) : Comics
}