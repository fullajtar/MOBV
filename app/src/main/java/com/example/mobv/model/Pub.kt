package com.example.mobv.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Pubs (
    var elements: List<Pub>? = null
){}

@Parcelize
data class Tags(
    var name : String? = null,
    var opening_hours: String? = null,
//    var opening_hourscovid19: String? = null,
    var website: String? = null,
    var phone: String? = null
): Parcelable

@Parcelize
data class Pub (
    //    val name: String? = null
    var lat: Number? = null,
    var lon: Number? = null,
    var tags: Tags? = null
): Parcelable


