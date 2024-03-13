package com.rubens.conectamedicina.data.chat

import android.util.Log
import com.rubens.conectamedicina.data.notification.ApiService
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
    private val pushNotificationService: ApiService
) {
    private var session: WebSocketSession? = null
    val TAG = "loggingSession"



    private val _messageFlow = MutableSharedFlow<ChatMessage>()
    val messageFlow: SharedFlow<ChatMessage> get() = _messageFlow

    private val _chatError = MutableSharedFlow<String>()
    val chatError get() = _chatError

    suspend fun initChatSession(doctorId: String, userId: String){
        try{
            session = client.webSocketSession {
                url("ws://192.168.0.5:8085/chatTest")
            }
            sendInitialRoomIds(userId)
            receiveMessages()
        }catch (e: Exception){
            _chatError.emit("There was an error initializing the chat")
            Log.d(TAG, e.message.toString() + " at initChatSession")

        }

    }

    suspend fun restartChatSession(userId: String, chatMessage: ChatMessage){

        try{
                session = client.webSocketSession {
                    url("ws://192.168.0.5:8085/chatTest")
                }
                Log.d(TAG, "session: $session")
        }catch (timeout: ConnectTimeoutException){
            Log.d(TAG, timeout.message.toString() + " at restartChatSession")
            _chatError.emit("There was an error connecting to the chat")

        }catch (e: Exception){
            Log.d(TAG, e.message.toString() + " at restartChatSession")
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
            Log.d(TAG, e.message.toString() + " at sendInitialRoomIds")
            _chatError.emit("There was an error connecting to the chat")


        }



    }

    suspend fun sendMessage(chatMessage: ChatMessage){

        try{
            session?.send(Frame.Text(Json.encodeToString(ChatMessageMapDecoder("message", chatMessage))))
            //aqui ele ja enviou a mensagem com sucesso
            sendNotification(chatMessage)


        }catch (sessionCancelled: CancellationException){
            _chatError.emit("There was an error sending the message")

            coroutineScope {
                withContext(Dispatchers.IO){
                    restartChatSession(chatMessage.sender, chatMessage)
                    sendInitialRoomIds(chatMessage.sender)
                    session?.send(Frame.Text(Json.encodeToString(ChatMessageMapDecoder("message", chatMessage))))
                    //aqui ele já enviou a mensagem com sucesso
                    sendNotification(chatMessage)
                    receiveMessages()




                }
            }


        }catch (e: Exception){
            _chatError.emit("There was an error sending the message")


            Log.d(TAG, e.message.toString() + " at sendMessage")

        }

    }

    suspend fun receiveMessages(){
        val incoming = session?.incoming

        if(incoming != null){
            try {
                for(message in incoming){
                    message as? Frame.Text ?: continue
                    val jsonMessage = message.readText()
                    val newChatMessage = Json.decodeFromString<ChatMessage>(jsonMessage)
                    _messageFlow.emit(newChatMessage)
                }
            }catch (cancellation: CancellationException){
                _chatError.emit("There was an error receiving the messages. The server might be down")

                println("Error while receiving: ${cancellation.localizedMessage} at receiveMessages")
                //session is cancelled and can no longer be used. Session receives null
                session = null

            }
        }

    }

    suspend fun sendNotification(chatMessage: ChatMessage) {
            pushNotificationService.saveMessageNotification(title = chatMessage.sender, description = chatMessage.message, chatMessage)


    }




}