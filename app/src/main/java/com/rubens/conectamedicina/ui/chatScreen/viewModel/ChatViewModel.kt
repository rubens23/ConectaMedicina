package com.rubens.conectamedicina.ui.chatScreen.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubens.conectamedicina.data.chat.ChatDataSource
import com.rubens.conectamedicina.data.chat.ChatKtorClient
import com.rubens.conectamedicina.data.chat.ChatMessage
import com.rubens.conectamedicina.data.logging.LogManager
import com.rubens.conectamedicina.data.notification.PushNotificationManager
import com.rubens.conectamedicina.data.notification.PushNotificationsService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.websocket.WebSockets
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatDataSource: ChatDataSource,
    private val logManager: LogManager,
    private val pushNotificationManager: PushNotificationManager
): ViewModel() {
    private val client: ChatKtorClient = ChatKtorClient(
        HttpClient(CIO){
            install(Logging)
            install(WebSockets)
        },
        logManager,
        pushNotificationManager
    )


    private var initiatedChat = false
    private var gotMessagesFromChatDatasource = false

    private val _chatError = mutableStateOf("")
    val chatError get() = _chatError

    //private val _messagesListState = mutableStateListOf<ChatMessage>()
    private val _messagesListState = MutableStateFlow<MutableList<ChatMessage>>(mutableListOf())
    val messagesListState get() = _messagesListState.asStateFlow()

    init {

        startCollectiongChatErrors()
        startCollectingNewMessages()

    }




    private fun startCollectiongChatErrors() {
        viewModelScope.launch {
            client.chatError.collect{
                _chatError.value = it

            }
        }
    }

    private fun startCollectingNewMessages() {

        viewModelScope.launch {

                client.messageFlow.collect{


                    _messagesListState.value = messagesListState.value.toMutableList().apply {
                        add(it)
                    }





                }


        }

    }


    fun sendNewChatMessageToServer(chatMessage: ChatMessage, userName: String) {
        viewModelScope.launch {
            client.sendMessage(chatMessage, userName)

        }

    }

    fun initChatRoom(idUser: String){
        viewModelScope.launch {
            if(!initiatedChat){
                client.initChatSession(idUser)
                initiatedChat = true

            }



        }
    }

    fun getMessagesIfTheyExist(idDoutor: String, idCliente: String) {
        viewModelScope.launch {

            //if there are any unwanted recomposes this wont make additional calls to the chatDataSource
            //the call to the chatDataSource should be made only once in the lifetime of the viewModel
            if(!gotMessagesFromChatDatasource){
                val chatMessages = chatDataSource.getChatMessages(idDoutor, idCliente)
                if (chatMessages != null){

                    _messagesListState.value = messagesListState.value.toMutableList().apply {
                        addAll(chatMessages.messages)
                    }
                    Log.d("solvingListBug", "eu peguei mensagens do database de chat. Lista depois do addAll: $messagesListState")


                    //_messagesListState.addAll(chatMessages.messages)
                    gotMessagesFromChatDatasource = true

                }else{
                    //even if there wasnt any messages, it tried to took the messages, so there shouldnt be anymore calls do the database
                    Log.d("solvingListBug", "to no else do gotMessagesFromChatDataSource: $messagesListState")

                    gotMessagesFromChatDatasource = true
                }
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