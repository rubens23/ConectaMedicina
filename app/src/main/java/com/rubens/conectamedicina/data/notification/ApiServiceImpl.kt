package com.rubens.conectamedicina.data.notification

import android.util.Log
import com.onesignal.OneSignal
import com.rubens.conectamedicina.data.chat.ChatMessage
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * essa api não será mais usada
 * ela será substituida pela solução ktor + fcm
 */
class ApiServiceImpl(
    private val client: HttpClient
): ApiService {
    private val TAG = "ApiServiceImpl"
    override suspend fun saveMessageNotification(
        title: String,
        description: String,
        chatMessage: ChatMessage
    ) {
        Log.e(TAG, "title: $title description $description")

        try {
            client.post<String>{
                header(HttpHeaders.ContentType, "application/json")
                url(ApiService.SEND_NOTIFICATION)
                body = Json.encodeToString(NotificationSaveMapDecoder("messageNotification",    NotificationDto(
                    username = chatMessage.receiver,
                    notification = NotificationBody(
                        title = title,
                        body = chatMessage.message
                    )))

                )
            }
//            client.post<String>{
//                url(ApiService.SEND_NOTIFICATION)
//                parameter("title", title)
//                parameter("description", description)
//
//            }

        }catch (e: Exception){
            Log.e(TAG, e.message.toString())

        }
    }

    override suspend fun createUser(userName: String, name: String) {
        try {
            client.get<String>{
                url(ApiService.CREATE_NOTIFICATION_USER)
                parameter("userName", userName)
                parameter("name", name)

            }
        }catch (e: Exception){
            Log.e(TAG, e.message.toString())

        }
    }

    override suspend fun changeUserExternalId(username: String) {
        OneSignal.setExternalUserId(username)
    }
}

