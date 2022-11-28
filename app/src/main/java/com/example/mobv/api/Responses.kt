package com.example.mobv.api

import com.example.mobv.model.Pub

data class Documents(
    var documents: MutableList<Pub>
)

data class UserResponse(
    var uid: Int,
    var access: String,
    var refresh: String,
)

data class BarListResponse(
    val bar_id: String,
    val bar_name: String,
    val bar_type: String,
    val lat: Double,
    var lon: Double,
    var users: Int
)

data class FriendResponse(
    val user_id: String,
    val user_name: String,
    val bar_id: String?,
    val bar_name: String?,
    val time: String?,
    val bar_lat: Double?,
    var bar_lon: Double?,
)