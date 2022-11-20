package com.example.mobv.api

data class BodyGetAllPubs (
    val collection: String = "bars",
    val database: String = "mobvapp",
    val dataSource: String = "Cluster0"){}