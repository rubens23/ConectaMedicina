package com.rubens.conectamedicina.data.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val sender: String,
    val message: String,
    val timestamp: String,
    val senderType: String,
    var chatId: String,
    val receiver: String
)

