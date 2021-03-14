package com.example.postapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locally_deleted_post_ids")
data class LocallyDeletedPostID(
        @PrimaryKey(autoGenerate = false)
        val deletedPostID: String
)