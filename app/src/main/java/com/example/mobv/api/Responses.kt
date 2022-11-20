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