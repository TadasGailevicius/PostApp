package com.example.postapp.data.remote

import com.example.postapp.data.local.entities.Post
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PostApi {

    @GET("/getPosts")
    suspend fun getPosts(): Response<List<Post>>

    @POST("/addPost")
    suspend fun addPost(
        @Body post: Post
    ): Response<ResponseBody>
}