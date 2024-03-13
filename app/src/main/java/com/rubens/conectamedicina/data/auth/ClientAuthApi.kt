package com.rubens.conectamedicina.data.auth

import com.rubens.conectamedicina.data.user.UserSecret
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ClientAuthApi {

    @POST("signup")
    suspend fun signUp(
        @Body request: SignUpRequest
    )

    @POST("signin")
    suspend fun signIn(
        @Body request: SignInRequest
    ): TokenResponse

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )

    @GET("secret")
    suspend fun getUserSecret(
        @Header("Authorization") token: String

    ): UserSecret
}