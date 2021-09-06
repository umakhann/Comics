package com.example.comics.model

import android.os.Parcelable
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "books")
@Parcelize
data class Book(@PrimaryKey val id: Int,
                val title: String,
                val description: String,
                @Embedded var thumbnail: Image,
                @Embedded val series: Name,
                val pageCount: Int,
                @Embedded val characters: Characters,

                val variants: List<Name>,
                val prices: List<Price>) : Parcelable {

    @Ignore
    lateinit var creators: Creators

    @Parcelize
    data class Image(val path: String, val extension: String) : Parcelable

    @Parcelize
    data class Name(val name: String, val resourceURI: String) : Parcelable

    @Parcelize
    data class Price(val price: String) : Parcelable

    @Parcelize
    data class Creators(val items: List<TempCreator>, val available: Int) : Parcelable

    @Parcelize
    data class Characters(val collectionURI: String, val available: Int) : Parcelable



}

    class NameConverter {

        companion object {
            val gson = Gson()
        }

        @TypeConverter
        fun fromList(list: List<Book.Name>): String? {
            return list.let { Gson().toJson(list) }
        }

        @TypeConverter
        fun toList(string: String): List<Book.Name> {
            val itemType = object : TypeToken<List<Book.Name>>() {}.type
            return Gson().fromJson(string, itemType)
        }

    }


        class PriceConverter {

            companion object {
                val gson = Gson()
            }

            @TypeConverter
            fun fromList(list: List<Book.Price>): String? {
                return list.let { Gson().toJson(list) }
            }

            @TypeConverter
            fun toList(string: String): List<Book.Price> {
                val itemType = object : TypeToken<List<Book.Price>>() {}.type
                return Gson().fromJson(string, itemType)
            }



        }
