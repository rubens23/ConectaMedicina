package com.rubens.conectamedicina.ui.chatScreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.rubens.conectamedicina.R
import com.rubens.conectamedicina.data.chat.ChatMessage
import com.rubens.conectamedicina.ui.chatScreen.viewModel.ChatViewModel

@Composable
fun ClientDoctorChatScreenLayout(
    viewModel : ChatViewModel,
    doctorUsername: String,
    userUsername: String,
    snackbarHostState: SnackbarHostState
){

    viewModel.initChatRoom(doctorUsername, userUsername)
    viewModel.getMessagesIfTheyExist(doctorUsername, userUsername)

    val chatError by remember { viewModel.chatError }



    val messagesList = remember { mutableStateOf<ArrayList<ChatMessage>>(ArrayList()) }

    if(chatError != ""){
        LaunchedEffect(Unit){
            snackbarHostState.showSnackbar(message = chatError,
                actionLabel = "Ok")

        }
    }




    LaunchedEffect(Unit){
        viewModel.messagesResult.collect{
            //aqui eu recebo um MutableList<ChatMessage>
            messagesList.value = ArrayList(messagesList.value).apply {
                addAll(it)
            }
        }
    }

    LaunchedEffect(Unit){
        viewModel.notificationsResult.collect{
            chatMessage->
            //viewModel.saveMessageNotification(chatMessage)
        }
    }



    Column(modifier = Modifier.fillMaxHeight()){

        ChatScreen(messagesList, modifier = Modifier.weight(1f), viewModel)
        MessageInput(viewModel, userUsername, doctorUsername)


    }



}

@Preview
@Composable
fun ChatScreenPreview(){
//    val messages = listOf(
//        ChatMessage("rubens@teste", "eu sou o user e essa é a primeira mensagem", "123456789", "user"),
//        ChatMessage("paulo@teste", "eu sou o doutor e essa é a primeira mensagem", "123456790", "doctor"),
//        ChatMessage("rubens@teste", "eu sou o user e essa é a segunda mensagem", "123456791", "user"),
//        ChatMessage("paulo@teste", "eu sou o doutor e essa é a segunda mensagem", "123456792", "doctor"),
//    )

    Column(modifier = Modifier.fillMaxHeight()) {
        val messageText = remember { mutableStateOf("") }
//        TestChatScreen(messages = messages, modifier = Modifier.weight(1f))
//        MessageInput(messageText){
//
//        }

    }

}

@Composable
fun TestChatScreen(messages: List<ChatMessage>, modifier: Modifier = Modifier){
    LazyColumn(modifier = modifier) {
        items(messages){
            message->
            //ChatMessageItem(message)
        }
    }
}

@Composable
fun ChatScreen(
    messagesList: MutableState<ArrayList<ChatMessage>>,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel
){

    LazyColumn(modifier = modifier,
    reverseLayout = false) {
        items(messagesList.value){
                message->
            ChatMessageItem(message, viewModel)
        }
    }

}

@Composable
fun ChatMessageItem(message: ChatMessage,viewModel: ChatViewModel
) {

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp



    val backgroundColor = if (message.senderType == "user") {

        Color(android.graphics.Color.parseColor("#43c2ff"))
        } else {


        Color(android.graphics.Color.parseColor("#D7F0FC"))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = if (message.senderType == "user") {
                Arrangement.End
            } else {
                Arrangement.Start
            }
        ) {


            // Display the message in a different color based on senderType
            Box(
                modifier = Modifier
                    .background(color = backgroundColor,
                        shape = RoundedCornerShape(8.dp))
                    .wrapContentHeight()


            ) {
                Row(modifier = Modifier
                    .widthIn(max = screenWidth * 0.7f)) {
                    Text(
                        text = message.message,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(8.dp)
                            .weight(weight = 1f,
                                fill = false)
                            .background(Color.Green),
                        fontSize = 18.sp,
                        maxLines = 7
                    )
                    Text(
                        text = viewModel.formatTimestampToLocalizedTime(message.timestamp.toLong()),
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .padding(end = 10.dp, bottom = 10.dp),
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }

            }



        }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(
    viewModel: ChatViewModel,
    idCliente: String,
    doctorId: String
){

    val messageText = remember { mutableStateOf("") }


    val chatMessage = ChatMessage(
        sender = idCliente,
        message = messageText.value,
        timestamp = System.currentTimeMillis().toString(),
        senderType = "user",
        chatId = "$doctorId-$idCliente",
        receiver = doctorId
    )


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        OutlinedTextField(
            value = messageText.value,
            onValueChange = { messageText.value = it },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    // Handle sending the message here
                    sendMessage(messageText.value)
                    // Clear the text field after sending
                    messageText.value = ""
                }
            ),
            placeholder = { Text("Type a message...") },
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color(0xFF43C2FF),
                focusedBorderColor = Color(0xFF43C2FF),
            )


        )

        IconButton(
            onClick = {
                // Handle sending the message here
                //sendMessage(messageText.value)
                // Clear the text field after sending
                messageText.value = ""
                viewModel.sendNewChatMessageToServer(chatMessage)
            }
        ) {
            Image(painter = painterResource(R.drawable.baseline_send_24),
                contentDescription = "Send",
                colorFilter = ColorFilter.tint(color = Color(android.graphics.Color.parseColor("#43c2ff"))),
                modifier = Modifier.size(38.dp)
            )
        }
    }

}

fun sendMessage(message: String) {
    println("Sending message: $message")
}