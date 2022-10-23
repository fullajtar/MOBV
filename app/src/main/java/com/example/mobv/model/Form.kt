package com.example.mobv.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Form(
    val name : String ="",
    val restaurantName : String= "",
    val latitude : Number = 0,
    val longitude : Number = 0

) : Parcelable
