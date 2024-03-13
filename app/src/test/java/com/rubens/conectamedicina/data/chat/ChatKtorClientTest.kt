package com.rubens.conectamedicina.data.chat

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ChatKtorClientTest{
    @Test
    fun testInitChatSession() = runTest{

        val mockEngine = MockEngine{
                request->
            respond(
                content = ByteReadChannel("""{"ip":"127.0.0.1"}"""),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine)

        val chatKtorClient = ChatKtorClient(client)

        chatKtorClient.initChatSession("", "rubens@teste.com")
        chatKtorClient.sendMessage(
            ChatMessage(
                sender = "rubens@teste.com",
                message = "testing sending a message",
                timestamp = "1234",
                senderType = "user",
                chatId = "",
                receiver = "carlos@teste.com"

            )
        )



    }








}
