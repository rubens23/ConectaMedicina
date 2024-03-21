package com.rubens.conectamedicina.data.chat

import android.content.SharedPreferences
import android.util.Log

class ChatDataSourceImpl(private val apiChat: ApiChat,
                         private val preferences: SharedPreferences): ChatDataSource {

   val tag = "ChatDataSourceImpl"
    override suspend fun getChatMessages(idDoctor: String, idUser: String): ChatRoom? {
        return try {
            val token = preferences.getString("jwt", null)?: return null
            val result = apiChat.getChatById("Bearer $token", idUser, idDoctor)
            result
        }catch (e: Exception){
            Log.e(tag, "error on getChatMessages ${e.message}")
            null
        }

    }
}