package com.rubens.conectamedicina.data.notification

import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rubens.conectamedicina.data.user.UserDataSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PushNotificationService: FirebaseMessagingService() {

    @Inject
    lateinit var userDataSource: UserDataSource
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val TAG = "PushNotificationService"

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

        override fun onNewToken(token: String) {
            super.onNewToken(token)
            Log.d(TAG, "To aqui no inicio do pushNotificationService")
            "to aqui"

            // Update server(save new token to user entity in the database)
            scope.launch {
                Log.d(TAG, "To aqui no inicio do pushNotificationService no escopo de coroutine")
                val username = sharedPreferences.getString("username", null)?:return@launch
                Log.d(TAG, "To $username")
                userDataSource.updateUserNotificationToken(token, username)

            }


        }

        override fun onMessageReceived(message: RemoteMessage) {
            super.onMessageReceived(message)

            // Respond to received messages
        }

    override fun onDestroy() {
        super.onDestroy()

        job.cancel()
    }

}
