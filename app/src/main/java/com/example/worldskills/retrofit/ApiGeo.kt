package com.example.worldskills.retrofit

import com.example.worldskills.model.Place
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGeo {
    @GET("reverse")
    suspend fun getName(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("format") format: String = "jsonv2"): Place
}