package com.rubens.conectamedicina.ui.notificatioScreen.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rubens.conectamedicina.R
import com.rubens.conectamedicina.data.notification.AllNotifications
import com.rubens.conectamedicina.data.notification.Notification
import com.rubens.conectamedicina.shimmertutorial.ShimmerNotificationItem
import com.rubens.conectamedicina.ui.notificatioScreen.viewModel.NotificationViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val TAG = "NotificationScreenLayout"

@Composable
fun NotificationScreenLayout(viewModel: NotificationViewModel,
                             snackbarHostState: SnackbarHostState) {



    val notifications = remember { mutableStateOf<AllNotifications?>(null) }
    val errorGettingNotifications by remember { viewModel.notificationResponseError }
    val loadingNotifications by remember { viewModel.notificationsLoading }



    LaunchedEffect(Unit) {
        viewModel.notificationResult.collect {
            val sortedList = it.notifications.sortedByDescending { it.notificationTimestamp }
            notifications.value = AllNotifications(
                notifications = sortedList
            )
        }

    }

    if(errorGettingNotifications != ""){
        LaunchedEffect(Unit){
            viewModel.setLoadingNotifications(false)
                snackbarHostState.showSnackbar(message = errorGettingNotifications,
                    actionLabel = "Ok")


        }


    }






    Column(modifier = Modifier.padding(top = 16.dp)) {

        Text(
            text = "Notifications",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
        )

        if (notifications.value != null) {

            ShimmerNotificationItem(
                isLoading = loadingNotifications,
                contentAfterLoading = {
                    LazyColumn {
                        items(notifications.value!!.notifications) { notification ->
                            NotificationScreenItem(notification, viewModel)
                        }

                    }
                }
            )


        }else{

            ShimmerNotificationItem(
                isLoading = loadingNotifications,
                contentAfterLoading = {

                }
            )

        }


    }


}

@Composable
fun NotificationScreenItem(notification: Notification, viewModel: NotificationViewModel) {

    val notificationHasBeenSeen = remember { mutableStateOf(notification.seen) }

    Column(modifier = Modifier.padding(start = 6.dp, bottom = 20.dp)) {
        Row {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                if (notificationHasBeenSeen.value) {
                    Box(
                        modifier = Modifier.size(6.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )

                } else {
                    Box(
                        modifier = Modifier.size(6.dp)
                            .clip(CircleShape)
                            .background(Color.Red)

                    )

                }


            }

            Spacer(modifier = Modifier.width(6.dp))
            Image(
                painter = painterResource(R.drawable.message_notification_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(15.dp)
                    .offset(y = 5.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Column {
                Row {
                    Text(
                        text = notification.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.offset(y = (-3).dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = viewModel.getHowLongAgoNotificationWasSent(notification.notificationTimestamp),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(end = 16.dp)
                            .align(Alignment.Bottom)
                            .offset(y = (-6).dp)
                    )
                }

                Text(
                    text = notification.notificationBody,
                    modifier = Modifier.padding(end = 16.dp, top = 6.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (!notificationHasBeenSeen.value) "Mark as seen" else "",
                    modifier = Modifier
                        .padding(end = 16.dp, top = 10.dp)
                        .clickable {
                            notificationHasBeenSeen.value = true
                            viewModel.markNotificationAsSeen(notification.idNotification)
                        },
                    color = Color.Blue
                )

            }

        }

    }
}

@Preview
@Composable
fun NotificationScreenLayoutPreview() {
    //NotificationScreenLayout()
}

