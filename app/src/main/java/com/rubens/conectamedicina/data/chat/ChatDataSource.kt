package com.rubens.conectamedicina.data.chat

interface ChatDataSource {
    suspend fun getChatMessages(idDoctor: String, idUser: String): ChatRoom?
}