package com.rubens.conectamedicina.data.notification

import kotlinx.serialization.Serializable

@Serializable
data class AllNotifications(
    val notifications: List<Notification>
)
