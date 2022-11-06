package com.example.mobv.retrofit

import com.google.firebase.firestore.local.LruGarbageCollector

data class QuoteList(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<LruGarbageCollector.Results>,
    val totalCount: Int,
    val totalPages: Int
)