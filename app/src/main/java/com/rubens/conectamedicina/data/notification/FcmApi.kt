package com.rubens.conectamedicina.data.notification

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface FcmApi {

    @POST("/send")
    suspend fun sendMessage(
        @Header("Authorization") authToken: String,
        @Body body: SendNotificationDto
    )
}