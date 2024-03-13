package com.rubens.conectamedicina.ui.chatScreen.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubens.conectamedicina.data.chat.ChatDataSource
import com.rubens.conectamedicina.data.chat.ChatKtorClient
import com.rubens.conectamedicina.data.chat.ChatMessage
import com.rubens.conectamedicina.data.models.DoctorClientCommunication
import com.rubens.conectamedicina.data.notification.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.websocket.WebSockets
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatDataSource: ChatDataSource,
    private val pushNotificationService: ApiService
): ViewModel() {
    private val client: ChatKtorClient = ChatKtorClient(
        HttpClient(CIO){
            install(Logging)
            install(WebSockets)
        },
        pushNotificationService
    )


    private val messagesChannel = Channel<MutableList<ChatMessage>>()
    private val notificationsChannel = Channel<ChatMessage>()
    val messagesResult = messagesChannel.receiveAsFlow()
    val notificationsResult = notificationsChannel.receiveAsFlow()

    private val _chatError = mutableStateOf("")
    val chatError get() = _chatError




    init {

        viewModelScope.launch {
            client.chatError.collect{
                _chatError.value = it

            }
        }

        viewModelScope.launch {
            client.messageFlow.collect{
                messagesChannel.send(mutableListOf(it))
                notificationsChannel.send(it)

            }
        }

    }



    fun sendNewChatMessageToServer(chatMessage: ChatMessage) {
        viewModelScope.launch {
            client.sendMessage(chatMessage)

        }

    }

    fun initChatRoom(idDoutor: String, idUser: String){
        viewModelScope.launch {
            client.initChatSession(idDoutor,idUser)

        }
    }

    fun getMessagesIfTheyExist(idDoutor: String, idCliente: String) {
        viewModelScope.launch {
            val chatMessages = chatDataSource.getChatMessages(idDoutor, idCliente)
            if (chatMessages != null){
                messagesChannel.send(chatMessages.messages)
            }

        }

    }



    fun formatTimestampToLocalizedTime(timestamp: Long): String {
        val dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
        val date = Date(timestamp)
        return dateFormat.format(date)
    }


}

//todo resolver o erro no route to host na tela de chat ao enviar uma mensagem para marcus no samsung