package com.example.postapp.data.local

import androidx.room.Dao
import androidx.room.Query
import com.example.postapp.data.local.entities.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM posts WHERE id = :postID")
    suspend fun getPostById(postID: String): Post?

    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAllPosts(): Flow<List<Post>>
}