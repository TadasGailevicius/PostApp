package com.example.postapp.data.remote

import com.example.postapp.data.remote.entities.JsonplaceholderPost
import retrofit2.Response
import retrofit2.http.GET

interface JsonplaceholderPostsApi {
    @GET("/posts")
    suspend fun getJPPosts(): Response<List<JsonplaceholderPost>>
}