package com.example.postapp.repositories

import android.app.Application
import com.example.postapp.data.local.PostDao
import com.example.postapp.data.local.entities.LocallyDeletedPostID
import com.example.postapp.data.local.entities.Post
import com.example.postapp.data.remote.JsonplaceholderPostsApi
import com.example.postapp.data.remote.JsonplaceholderUsersApi
import com.example.postapp.data.remote.PostApi
import com.example.postapp.data.remote.requests.DeletePostRequest
import com.example.postapp.other.Resource
import com.example.postapp.other.checkForInternetConnection
import com.example.postapp.other.networkBoundResource
import retrofit2.Response

import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postDao: PostDao,
    private val postApi: PostApi,
    private val jsonplaceholderPostsApi: JsonplaceholderPostsApi,
    private val jsonplaceholderUsersApi: JsonplaceholderUsersApi,
    private val context: Application
) {
    suspend fun getJPPosts() =
        jsonplaceholderPostsApi.getJPPosts()

    suspend fun getJPUsers() =
        jsonplaceholderUsersApi.getJPUsers()

    suspend fun insertPost(post: Post) {
        val response = try {
            postApi.addPost(post)
        } catch (e: Exception) {
            null
        }

        if(response != null && response.isSuccessful) {
            postDao.insertPost(post.apply { isSynced = true })
        } else {
            postDao.insertPost(post)
        }
    }

    suspend fun insertPosts(posts: List<Post>) {
        posts.forEach { insertPost(it) }
    }

    suspend fun deletePost(postID: String) {
        val response = try {
            postApi.deletePost(DeletePostRequest(postID))
        } catch (e: Exception) {
            null
        }
        postDao.deletePostById(postID)
        if(response == null || !response.isSuccessful) {
            postDao.insertLocallyDeletedPostID(LocallyDeletedPostID(postID))
        } else {
            deleteLocallyDeletedPostID(postID)
        }
    }

    fun observePostByID(postID: String) = postDao.observePostById(postID)

    suspend fun deleteLocallyDeletedPostID(deletedPostID: String) {
        postDao.deleteLocallyDeletedPostID(deletedPostID)
    }

    suspend fun getPostById(postID: String) = postDao.getPostById(postID)

    private var curPostsResponse: Response<List<Post>>? = null

    suspend fun syncPosts() {
        val locallyDeletedPostIDs = postDao.getAllLocallyDeletedPostIDs()
        locallyDeletedPostIDs.forEach { id -> deletePost(id.deletedPostID) }

        val unsyncedPosts = postDao.getAllUnsyncedPosts()
        unsyncedPosts.forEach { post -> insertPost(post) }

        curPostsResponse = postApi.getPosts()
        curPostsResponse?.body()?.let { posts ->
            postDao.deleteAllPosts()
            insertPosts(posts.onEach { post -> post.isSynced = true })
        }
    }

    fun getAllPosts(): Flow<Resource<List<Post>>> {
        return networkBoundResource(
                query = {
                    postDao.getAllPosts()
                },
                fetch = {
                    syncPosts()
                    curPostsResponse
                },

                saveFetchResult = { response ->
                    response?.body()?.let {
                        insertPosts(it.onEach { post -> post.isSynced = true })
                    }
                },

                shouldFetch = {
                    checkForInternetConnection(context)
                }
        )
    }


}
