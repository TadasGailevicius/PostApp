package com.example.postapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.postapp.data.local.entities.LocallyDeletedPostID
import com.example.postapp.data.local.entities.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post)

    @Query("DELETE FROM posts WHERE id = :postID")
    suspend fun deletePostById(postID: String)

    @Query("DELETE FROM posts WHERE isSynced = 1")
    suspend fun deleteAllSyncedPosts()

    @Query("DELETE FROM posts")
    suspend fun deleteAllPosts()

    @Query("SELECT * FROM posts WHERE id = :postID")
    fun observePostById(postID: String): LiveData<Post>

    @Query("SELECT * FROM posts WHERE id = :postID")
    suspend fun getPostById(postID: String): Post?

    @Query("SELECT * FROM posts")
    fun getAllPosts(): Flow<List<Post>>

    @Query("SELECT * FROM posts WHERE isSynced = 0")
    suspend fun getAllUnsyncedPosts(): List<Post>

    @Query("SELECT * FROM locally_deleted_post_ids")
    suspend fun getAllLocallyDeletedPostIDs(): List<LocallyDeletedPostID>

    @Query("DELETE FROM locally_deleted_post_ids WHERE deletedPostID = :deletedPostID")
    suspend fun deleteLocallyDeletedPostID(deletedPostID: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocallyDeletedPostID(locallyDeletedPostID: LocallyDeletedPostID)


}