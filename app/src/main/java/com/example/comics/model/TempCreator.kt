package com.example.comics.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TempCreator(val name: String,
                  val role: String) : Parcelable {
}