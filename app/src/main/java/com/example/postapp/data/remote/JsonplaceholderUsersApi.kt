package com.example.postapp.data.remote

import com.example.postapp.data.remote.entities.JsonplaceholderUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonplaceholderUsersApi {
    @GET("/users")
    suspend fun getJPUsers(): Response<List<JsonplaceholderUser>>

    @GET("/users/{userID}")
    suspend fun getJPUserById(userID: Int): Response<JsonplaceholderUser>
}