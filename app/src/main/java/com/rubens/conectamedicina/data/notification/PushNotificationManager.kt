package com.rubens.conectamedicina.data.notification

import com.rubens.conectamedicina.data.chat.ChatMessage

class PushNotificationManager(
    private val pushNotificationService: PushNotificationsService
) {
    suspend fun sendNotification(chatMessage: ChatMessage) {
        pushNotificationService.saveMessageNotification(title = chatMessage.sender, description = chatMessage.message, chatMessage)


    }
}