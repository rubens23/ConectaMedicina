package com.rubens.conectamedicina.data.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationSaveMapDecoder(
    val type: String,
    val notificationDto: NotificationDto
)
