package com.rubens.conectamedicina.data.notification

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val sender: String,
    val receiver: String,
    val notificationIcon: Int,
    val title: String,
    val notificationBody: String,
    val notificationTimestamp: String,
    val seen: Boolean = false,
    val idNotification: String
)


