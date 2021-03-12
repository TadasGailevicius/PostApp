package com.example.postapp.data.remote

import com.example.postapp.data.local.entities.Post
import retrofit2.Response
import retrofit2.http.GET

interface PostApi {

    @GET("/getPosts")
    suspend fun getNotes(): Response<List<Post>>
}