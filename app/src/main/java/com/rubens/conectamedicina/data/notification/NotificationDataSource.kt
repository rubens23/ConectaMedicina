package com.rubens.conectamedicina.data.notification

interface NotificationDataSource {

    suspend fun getUserNotifications(username: String): AllNotifications?

    suspend fun updateThatNotificationHasBeenSeen(notificationId: String)
}