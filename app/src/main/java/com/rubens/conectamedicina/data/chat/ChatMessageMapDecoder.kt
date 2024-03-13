package com.rubens.conectamedicina.data.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageMapDecoder(
    val type: String,
    val chatMessage: ChatMessage
)

