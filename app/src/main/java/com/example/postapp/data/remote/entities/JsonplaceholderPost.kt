package com.example.postapp.data.remote.entities

import com.google.gson.annotations.Expose

data class JsonplaceholderPost(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int,
    @Expose(deserialize = false, serialize = false)
    var isSynced: Boolean = false,
)