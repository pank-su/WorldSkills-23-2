package com.example.worldskills.retrofit

import com.example.worldskills.model.Add
import com.example.worldskills.model.Analyze
import com.example.worldskills.model.Message
import com.example.worldskills.model.Profile
import com.example.worldskills.model.TokenDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiMedic {
    @Headers("Accept: application/json")
    @POST("sendCode")
    suspend fun sendCode(@Header("email") email: String): Message

    @Headers("Accept: application/json")
    @POST("signin")
    suspend fun signIn(@Header("email") email: String, @Header("code") code: Int): TokenDTO

    @POST("createProfile")
    suspend fun createProfile(@Body model: Profile, @Header("Authorization") token: String): Profile

    @GET("news")
    suspend fun news(): List<Add>

    @GET("catalog")
    suspend fun catalog(): List<Analyze>

    @PUT("updateProfile")
    suspend fun updateProfile(
        @Body profile: Profile,
        @Header("Authorization") token: String
    ): Message


}