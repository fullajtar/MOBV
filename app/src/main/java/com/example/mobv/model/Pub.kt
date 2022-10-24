package com.example.mobv.model

data class Pubs (
    var elements: List<Pub>? = null
){}

data class Tags(
    var name : String? = null
){}

data class Pub (
    //    val name: String? = null
    var lat: Number? = null,
    var lon: Number? = null,
    var tags: Tags? = null
){}


