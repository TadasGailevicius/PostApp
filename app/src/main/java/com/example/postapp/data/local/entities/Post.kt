package com.example.postapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.util.*

@Entity(tableName = "posts")
data class Post (
        val userId: Int,
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val title: String,
        val body: String,
        @Expose(deserialize = false, serialize = false)
        var isSynced: Boolean = false,

)