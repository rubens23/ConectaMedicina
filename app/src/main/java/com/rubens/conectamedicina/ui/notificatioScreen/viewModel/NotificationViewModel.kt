package com.rubens.conectamedicina.ui.notificatioScreen.viewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubens.conectamedicina.data.notification.AllNotifications
import com.rubens.conectamedicina.data.notification.NotificationDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationDataSource: NotificationDataSource,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    val TAG = "NotificationViewModel"

    private val notificationsChannel = Channel<AllNotifications>()
    val notificationResult = notificationsChannel.receiveAsFlow()

    private val _notificationResponseError = mutableStateOf("")
    val notificationResponseError get() = _notificationResponseError

    private val notificationsCountChannel = Channel<Int>()
    val notificationCountResult = notificationsCountChannel.receiveAsFlow()

    private var _notificationsLoading = mutableStateOf(true)
    val notificationsLoading: State<Boolean> get() = _notificationsLoading

    init {
        getAllUserNotification()

    }




    private fun getAllUserNotification() {
        viewModelScope.launch{
            val username = sharedPreferences.getString("username", null)?:return@launch
            val notifications = notificationDataSource.getUserNotifications(username)
            if(notifications != null){
                _notificationsLoading.value = false

                notificationsChannel.send(notifications)

            }else{


                _notificationResponseError.value = "There was an error getting all notifications"
            }
        }

    }

    fun getUnseenNotificationCount() {
        viewModelScope.launch{
            val username = sharedPreferences.getString("username", null)?:return@launch
            val notifications = notificationDataSource.getUserNotifications(username)
            if(notifications != null){
                val unseenNotificationsCount = notifications.notifications.count {!it.seen  }
                notificationsCountChannel.send(unseenNotificationsCount)

            }
        }
    }

    fun markNotificationAsSeen(idNotification: String) {
        viewModelScope.launch{
            notificationDataSource.updateThatNotificationHasBeenSeen(idNotification)
        }
    }

    fun getHowLongAgoNotificationWasSent(notificationTimestamp: String): String {
        val currentTimestamp = System.currentTimeMillis()

        try {
            val notificationDate = Date(notificationTimestamp.toLong())
            val currentDate = Date(currentTimestamp)

            val diffInMillis = currentDate.time - notificationDate.time
            val days = TimeUnit.MILLISECONDS.toDays(diffInMillis)
            val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)

            return when {
                days > 0 -> "$days ${if (days == 1L) "dy" else "dys"}"
                hours > 0 -> "$hours ${if (hours == 1L) "hr" else "hrs"}"
                else -> "$minutes ${if (minutes == 1L) "mn" else "mns"}"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return "Invalid timestamp"
        }

    }

    fun setLoadingNotifications(isLoading: Boolean) {
        _notificationsLoading.value = isLoading

    }


}