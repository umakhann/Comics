package com.example.comics.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Creator(val id : String,
                   val firstName: String,
                   val lastName: String,
                   val name: String,
                   val role: String,
                   val results : List<Book>) : Parcelable {
}