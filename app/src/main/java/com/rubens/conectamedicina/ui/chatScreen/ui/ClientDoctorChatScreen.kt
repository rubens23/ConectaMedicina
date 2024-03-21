package com.rubens.conectamedicina.ui.chatScreen.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage

import com.rubens.conectamedicina.R
import com.rubens.conectamedicina.data.chat.ChatMessage
import com.rubens.conectamedicina.ui.chatScreen.viewModel.ChatViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun ClientDoctorChatScreenLayout(
    viewModel : ChatViewModel,
    doctorUsername: String,
    userUsername: String,
    snackbarHostState: SnackbarHostState,
    userName: String,
    goBackToDoctorDetails: ()->Unit,
    doctorPhotoUrl: String,
    doctorName: String,
    userPhotoUrl: String
){

    Log.d("testingUserName", "o userName do user na tela de chat é $userName")




    viewModel.initChatRoom(doctorUsername, userUsername)
    viewModel.getMessagesIfTheyExist(doctorUsername, userUsername)
    val chatError by remember { viewModel.chatError }
    val scrollState = rememberLazyListState()



    val messagesList by viewModel.messagesListState.collectAsStateWithLifecycle()




    if(chatError != ""){
        LaunchedEffect(Unit){
            snackbarHostState.showSnackbar(message = chatError,
                actionLabel = "Ok")

        }
    }


    LaunchedEffect(messagesList.size){

        if (messagesList.size > 0){
            Log.d("solvingListBug", "messagesList: $messagesList")
            scrollState.scrollToItem(messagesList.size - 1)

        }

    }



    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,

        ) {
        paddingValues->
        Column(modifier = Modifier
            .padding(paddingValues)){

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp)) {
                Spacer(Modifier.width(16.dp))



                FloatingActionButton(
                    onClick = {
                        goBackToDoctorDetails()
                    },
                    shape= CircleShape,
                    modifier = Modifier
                        .size(48.dp),
                    //.padding(start = 16.dp, top = 40.dp),
                    backgroundColor = Color(android.graphics.Color.parseColor("#43c2ff"))
                ){
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "voltar para tela principal",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = doctorName,
                    fontSize = 26.sp,
                    modifier = Modifier.weight(1f)
                        .padding(start = 10.dp),
                    color = Color(android.graphics.Color.parseColor("#43c2ff"))
                )


            }



            LazyColumn(modifier = Modifier.weight(1f),
                reverseLayout = false,
                state = scrollState) {
                itemsIndexed(messagesList){
                        index, message->


                    /*
                    current user não é igual a previous user
                    Se houver foto pode mostrar

                    doctor pode ter foto e user nao ter ou vice versa
                     */
                    val showPhoto = shouldShowPhoto(index, messagesList)
                    ChatMessageItem(message, viewModel, doctorPhotoUrl, showPhoto, userPhotoUrl)
                }
            }
            MessageInput(viewModel, userUsername, doctorUsername, userName)


        }
    }





}



@Composable
fun ChatMessageItem(
    message: ChatMessage, viewModel: ChatViewModel, doctorPhotoUrl: String, canShowPhoto: Boolean, userPhoto: String
) {





    val configuration = LocalConfiguration.current

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

            /*
            com essa logica atual tanto a foto do doc quanto a foto do user podem ser mostradas ao mesmo tempo
             */

            val showDocPhoto = doctorPhotoUrl != "docHasNoPhoto" && canShowPhoto

            if(showDocPhoto && message.senderType != "user"){

                AsyncImage(
                    model = doctorPhotoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(48.dp)
                        .clip(CircleShape)


                )

                Spacer(Modifier.width(10.dp))


            }else if(doctorPhotoUrl != "docHasNoPhoto"){
                Spacer(Modifier.width(48.dp))
                Spacer(Modifier.width(10.dp))
            }






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
                                fill = false),
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


            val showUserPhoto = userPhoto != "userHasNoPhoto" && canShowPhoto

            if(showUserPhoto && message.senderType == "user"){
                Spacer(Modifier.width(10.dp))

                AsyncImage(
                    model = userPhoto,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(48.dp)
                        .clip(CircleShape)


                )

            }else if(userPhoto != "userHasNoPhoto"){
                Spacer(Modifier.width(48.dp))
                Spacer(Modifier.width(10.dp))
            }





        }



}

@Composable
fun MessageInput(
    viewModel: ChatViewModel,
    idCliente: String,
    doctorId: String,
    userName: String
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
                viewModel.sendNewChatMessageToServer(chatMessage, userName)
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

fun shouldShowPhoto(index: Int, messagesList: List<ChatMessage>): Boolean {
    if(index == 0){
        return true
    }

    val currentSenderType = messagesList[index].senderType
    val previousSenderType = messagesList[index - 1].senderType

    return currentSenderType != previousSenderType

}