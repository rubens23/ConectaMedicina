package com.rubens.conectamedicina.data.chat

import android.content.SharedPreferences

class ChatDataSourceImpl(private val apiChat: ApiChat,
                         private val preferences: SharedPreferences): ChatDataSource {
    override suspend fun getChatMessages(idDoctor: String, idUser: String): ChatRoom? {
        return try {
            val token = preferences.getString("jwt", null)?: return null
            val result = apiChat.getChatById("Bearer $token", idUser, idDoctor)
            result
        }catch (e: Exception){
            e
            null
        }

    }
}