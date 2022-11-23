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