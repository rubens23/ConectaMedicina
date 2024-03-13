package com.rubens.conectamedicina.data.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    val username: String,
    val notification: NotificationBody
)