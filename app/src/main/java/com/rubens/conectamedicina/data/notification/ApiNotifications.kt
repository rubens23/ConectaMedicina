package com.rubens.conectamedicina.data.notification

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiNotifications {



//    @GET("allNotifications")
//    fun getUserNotificationsByUsername(
//        @Header("Authorization") token: String,
//        @Query("username") username: String
//    ): AllNotifications?

    fun getUserNotificationsByUsername(token: String, username: String
    ): AllNotifications?
}