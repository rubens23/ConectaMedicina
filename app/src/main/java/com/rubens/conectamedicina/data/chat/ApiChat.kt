package com.rubens.conectamedicina.data.chat

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiChat {

    @GET("chatMessages")
    suspend fun getChatById(
        @Header("Authorization") token: String,
        @Query("userId") userId: String,
        @Query("doctorId") doctorId: String
    ): ChatRoom?
}