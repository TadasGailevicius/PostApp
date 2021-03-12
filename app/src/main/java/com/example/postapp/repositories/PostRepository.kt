package com.example.postapp.repositories

import android.app.Application
import com.example.postapp.data.local.PostDao
import com.example.postapp.data.local.entities.Post
import com.example.postapp.data.remote.PostApi
import com.example.postapp.other.Resource
import com.example.postapp.other.checkForInternetConnection
import com.example.postapp.other.networkBoundResource
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postDao: PostDao,
    private val postApi: PostApi,
    private val context: Application
) {

    suspend fun insertPosts(posts: List<Post>) {
        posts.forEach { insertPost(it) }
    }

    suspend fun insertPost(post: Post) {
        val response = try {
            postApi.addPost(post)
        } catch (e: Exception) {
            null
        }

        suspend fun getPostById(postID: String) = postDao.getPostById(postID)

        fun getAllPosts(): Flow<Resource<List<Post>>> {
            return networkBoundResource(
                query = {
                    postDao.getAllPosts()
                },
                fetch = {
                    postApi.getPosts()
                },

                saveFetchResult = { response ->
                    response.body()?.let {
                        insertPosts(it)
                    }
                },

                shouldFetch = {
                    checkForInternetConnection(context)
                }
            )
        }


    }
}