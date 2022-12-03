package com.example.mobv.api

data class BodyGetAllPubs (
    val collection: String = "bars",
    val database: String = "mobvapp",
    val dataSource: String = "Cluster0"){}

data class BodySignUp (
    var name: String = "username25565",
    var password: String = "heslo25565"){}

data class UserRefreshRequest(
    val refresh: String
){}

data class BodyAddFriend(
    val contact: String
){}

data class BodyCheckInBar(
    val id: String?,
    val name: String?,
    val type: String?,
    val lat: Double? = 3.3,
    val lon: Double? = 33.3
)