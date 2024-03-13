package com.rubens.conectamedicina.data.auth

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface DoctorAuthApi {

    @POST("signup")
    suspend fun signUp(
        @Body request: SignUpRequest
    )

    @POST
    suspend fun signIn(
        @Body request: SignInRequest
    ): TokenResponse

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )
}