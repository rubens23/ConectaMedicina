package com.rubens.conectamedicina.data.notification

import com.rubens.conectamedicina.data.chat.ChatMessage

/**
 * essa api não será mais usada
 * ela será substituida pela solução ktor + fcm
 */
interface ApiService {

    suspend fun saveMessageNotification(
        title: String,
        description: String,
        chatMessage: ChatMessage
    )

    //I'm playing with this function to see how it works
    suspend fun createUser(userName: String, name: String)
    suspend fun changeUserExternalId(username: String)


    companion object{
        const val SEND_NOTIFICATION = "http://192.168.0.2:8081/saveMessageNotificationToDoctor"
        const val CREATE_NOTIFICATION_USER = "http://192.168.0.2:8092/createUser"
    }
}