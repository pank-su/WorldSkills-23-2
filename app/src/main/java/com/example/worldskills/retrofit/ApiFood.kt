package com.example.worldskills.retrofit

import com.example.worldskills.model.Message
import com.example.worldskills.model.TokenDTO
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiFood {
    @Headers("Accept: application/json")
    @POST("sendCode")
    suspend fun sendCode(@Header("email") email: String): Message

    @Headers("Accept: application/json")
    @POST("signin")
    suspend fun signIn(@Header("email") email: String, @Header("code") code: Int): TokenDTO
}