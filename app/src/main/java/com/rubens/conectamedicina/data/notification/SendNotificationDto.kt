package com.rubens.conectamedicina.data.notification

import kotlinx.serialization.Serializable

data class SendNotificationDto(
    val to: String?,
    val notification: NotificationBody
)
@Serializable
data class NotificationBody(
    val title: String,
    val body: String
)
