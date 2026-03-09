package com.example.a2faapplication.services

import com.example.a2faapplication.model.LoginRequest
import com.example.a2faapplication.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

}