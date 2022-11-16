package com.example.mobv.retrofit

import android.os.Parcelable
import com.example.mobv.model.Tags
import com.google.firebase.firestore.local.LruGarbageCollector
import kotlinx.android.parcel.Parcelize

data class QuoteList(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<LruGarbageCollector.Results>,
    val totalCount: Int,
    val totalPages: Int
)

data class Documents(
    var documents: MutableList<Pub>
)

@Parcelize
data class Pub (
    val id: Number? = null,
    var lat: Number? = null,
    var lon: Number? = null,
    var tags: Tags? = null
): Parcelable