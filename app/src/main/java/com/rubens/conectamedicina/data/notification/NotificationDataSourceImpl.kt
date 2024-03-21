package com.rubens.conectamedicina.data.notification

import android.content.SharedPreferences
import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationDataSourceImpl(
    private val apiNotifications: ApiNotifications,
    private val prefs: SharedPreferences,
    private val client: HttpClient

): NotificationDataSource {

    val TAG = "NotificationDataSourceImpl"

    override suspend fun getUserNotifications(username: String): AllNotifications? {



        return try{
            val token = prefs.getString("jwt", null)?: return null

            val response = client.get<String>{
                header(HttpHeaders.Authorization, "Bearer $token")
                url("http://192.168.0.2:8081/allNotifications")
                parameter("username", username)


            }

            Log.d(TAG, "chguei ate aqui onde obtive as notificações")
            Json.decodeFromString<AllNotifications>(response)

        }catch (e: Exception){
            Log.d(TAG, "caí na exceção ${e.message}")

            null
        }
    }

    override suspend fun updateThatNotificationHasBeenSeen(notificationId: String) {

        val token = prefs.getString("jwt", null)

        if(token != null){
            client.post<String>{
                header(HttpHeaders.Authorization, "Bearer $token")
                url("http://192.168.0.2:8081/markNotificationAsSeen")
                body = notificationId

            }
        }



    }


}