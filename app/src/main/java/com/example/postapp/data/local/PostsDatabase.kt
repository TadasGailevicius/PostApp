package com.example.postapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.postapp.data.local.entities.LocallyDeletedPostID
import com.example.postapp.data.local.entities.Post

@Database(
        entities = [Post::class, LocallyDeletedPostID::class],
        version = 1
)

@TypeConverters(Converters::class)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
}