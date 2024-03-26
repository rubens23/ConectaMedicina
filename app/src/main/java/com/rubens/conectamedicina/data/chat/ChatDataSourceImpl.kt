package com.rubens.conectamedicina.data.chat

import com.rubens.conectamedicina.data.auth.AuthTokenManager
import com.rubens.conectamedicina.data.logging.LogManager

class ChatDataSourceImpl(private val apiChat: ApiChat,
                         private val authTokenManager: AuthTokenManager,
    private val logManager: LogManager): ChatDataSource {

   val tag = "ChatDataSourceImpl"
    override suspend fun getChatMessages(idDoctor: String, idUser: String): ChatRoom? {
        return try {
            val token = authTokenManager.getToken()?: return null
            apiChat.getChatById("Bearer $token", idUser, idDoctor)
        }catch (e: Exception){
            logManager.printErrorLogs(tag, "error on getChatMessages ${e.message}")
            null
        }

    }
}