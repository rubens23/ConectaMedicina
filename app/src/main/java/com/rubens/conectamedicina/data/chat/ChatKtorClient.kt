package com.rubens.conectamedicina.data.chat

import android.util.Log
import com.rubens.conectamedicina.data.logging.LogManager
import com.rubens.conectamedicina.data.notification.PushNotificationManager
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.readText
import io.ktor.network.sockets.ConnectTimeoutException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException

class ChatKtorClient(
    private val client: HttpClient,
    private val logManager: LogManager,
    private val pushNotificationManager: PushNotificationManager

    ) {
    private var session: WebSocketSession? = null
    private val tag = "loggingSession"



    private val _messageFlow = MutableSharedFlow<ChatMessage>()
    val messageFlow: SharedFlow<ChatMessage> get() = _messageFlow

    private val _chatError = MutableSharedFlow<String>()
    val chatError get() = _chatError

    suspend fun initChatSession(userId: String){
        try{
            session = client.webSocketSession {
                url("ws://192.168.0.2:8085/chatTest")
            }
            sendInitialRoomIds(userId)
            receiveMessages()
        }catch (e: Exception){
            _chatError.emit("There was an error initializing the chat")
            logManager.printErrorLogs(tag, e.message.toString() + " at initChatSession")

        }

    }

    private suspend fun restartChatSession(userId: String, chatMessage: ChatMessage){

        try{
                session = client.webSocketSession {
                    url("ws://192.168.0.2:8085/chatTest")
                }
            logManager.printErrorLogs(tag, "session: $session")
        }catch (timeout: ConnectTimeoutException){
            logManager.printErrorLogs(tag, timeout.message.toString() + " at restartChatSession")
            _chatError.emit("There was an error connecting to the chat")

        }catch (e: Exception){
            logManager.printErrorLogs(tag, e.message.toString() + " at restartChatSession")
            _chatError.emit("There was an error connecting to the chat")


        }

    }

    private suspend fun sendInitialRoomIds(owner: String) {

        try{
            session?.send(Frame.Text(Json.encodeToString(mapOf(
                "type" to "initialRoomIds",
                "owner" to owner
            ))))
        }catch (e: Exception){
            logManager.printErrorLogs(tag, e.message.toString() + " at sendInitialRoomIds")
            _chatError.emit("There was an error connecting to the chat")


        }



    }

    suspend fun sendMessage(chatMessage: ChatMessage, userName: String){

        try{
            sendNewChatMessage(chatMessage, userName)

            //aqui ele ja enviou a mensagem com sucesso
            pushNotificationManager.sendNotification(chatMessage)


        }catch (sessionCancelled: CancellationException){
            _chatError.emit("There was an error sending the message")


            try {
                coroutineScope {
                    withContext(Dispatchers.IO){
                        restartChatSession(chatMessage.sender, chatMessage)
                        sendInitialRoomIds(chatMessage.sender)
                        sendNewChatMessage(chatMessage, userName)
                        //aqui ele já enviou a mensagem com sucesso
                        pushNotificationManager.sendNotification(chatMessage)
                        //receiveMessages()




                    }
                }
            }catch (e: Exception){
                logManager.printErrorLogs(tag, e.message.toString() + " at sendMessage")

            }



        }catch (e: Exception){
            _chatError.emit("There was an error sending the message")


            logManager.printErrorLogs(tag, e.message.toString() + " at sendMessage")

        }

    }

    private suspend fun sendNewChatMessage(chatMessage: ChatMessage, userName: String) {
        try {
            session?.send(Frame.Text(Json.encodeToString(ChatMessageMapDecoder("message", chatMessage, userName))))

        }catch (e: Exception){
            logManager.printErrorLogs(tag, e.message.toString() + " at sendNewChatMessage")

        }


    }

    private suspend fun receiveMessages() {

        val incoming = session!!.incoming

        try {
            for (message in incoming) {
                message as? Frame.Text ?: continue
                val jsonMessage = message.readText()
                val newChatMessage = Json.decodeFromString<ChatMessage>(jsonMessage)
                Log.d("solvingListBug", "recebi uma nova mensagem la do server websocket $newChatMessage")
                _messageFlow.emit(newChatMessage)
            }
        } catch (cancellation: CancellationException) {
            Log.d("investigatingChat3", "foi cancelado ${cancellation.message}")

            _chatError.emit("There was an error receiving the messages. The server might be down")

            session = null

        }


    }






}