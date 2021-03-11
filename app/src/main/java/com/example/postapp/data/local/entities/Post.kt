package com.example.postapp.data.local.entities

import androidx.room.Entity

@Entity(tableName = "posts")
data class Post (
    val userId: String,
    val id: String,
    val title: String,
    val body: String

)