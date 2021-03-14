package com.example.postapp.data.remote

import com.example.postapp.data.local.entities.Post
import com.example.postapp.data.remote.requests.DeletePostRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PostApi {

    @POST("/addPost")
    suspend fun addPost(
            @Body post: Post
    ): Response<ResponseBody>

    @POST("/deletePost")
    suspend fun deletePost(
            @Body deletePostRequest: DeletePostRequest
    ): Response<ResponseBody>

    @GET("/getPosts")
    suspend fun getPosts(): Response<List<Post>>
}