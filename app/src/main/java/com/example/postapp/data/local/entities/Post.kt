package com.example.postapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "posts")
data class Post (
        val userId: String,
        @PrimaryKey(autoGenerate = false)
        val id: String = UUID.randomUUID().toString(),
        val title: String,
        val body: String

)