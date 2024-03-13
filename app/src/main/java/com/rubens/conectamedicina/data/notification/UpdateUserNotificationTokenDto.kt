package com.rubens.conectamedicina.data.notification

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserNotificationTokenDto(
    val username: String,
    val token: String
)
