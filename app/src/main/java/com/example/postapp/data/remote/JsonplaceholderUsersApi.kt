package com.example.postapp.data.remote

import com.example.postapp.data.remote.entities.JsonplaceholderUser
import retrofit2.Response
import retrofit2.http.GET

interface JsonplaceholderUsersApi {
    @GET("/users")
    suspend fun getJPUsers(): Response<List<JsonplaceholderUser>>
}