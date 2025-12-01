package com.merteroglu286.auth.data.service

import com.merteroglu286.auth.data.requests.LoginRequest
import com.merteroglu286.auth.data.responses.LoginResponse
import com.merteroglu286.data.response.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ApiResponse<LoginResponse>>
}