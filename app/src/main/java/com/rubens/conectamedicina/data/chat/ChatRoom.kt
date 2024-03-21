package com.rubens.conectamedicina.data.chat

import kotlinx.serialization.Serializable


@Serializable
data class ChatRoom(
    val messages: MutableList<ChatMessage>,
    val doctor: String,
    val user: String
)



