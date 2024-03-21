package com.rubens.conectamedicina.data.user

import android.content.SharedPreferences
import android.util.Log
import com.rubens.conectamedicina.data.notification.UpdateUserNotificationTokenDto
import com.rubens.conectamedicina.data.storage.StorageDto

class UserDataSourceImpl(
    private val apiUsers: ApiUsers,
    private val prefs: SharedPreferences
): UserDataSource {

    private val TAG = "UserDataSourceImpl"

    override suspend fun getUserByUserId(userId: String): User? {
        val token = prefs.getString("jwt", null)?: return null
        return apiUsers.getUserById("Bearer $token", userId)
    }

    override suspend fun getUserProfilePictureByUsername(userUsername: String): String {
        return try {
            apiUsers.getUserProfilePictureByUsername(userUsername).photoUrl
        }catch (e: Exception){
            Log.e(TAG, "error on getUserProfilePictureByUsername ${e.message}")
            ""

        }
    }

    override suspend fun updateUserNotificationToken(fcmToken: String, username: String) {
        val token = prefs.getString("jwt", null)
        if(token != null){
            try {
                apiUsers.updateUserFcmToken(
                    "Bearer $token",
                    UpdateUserNotificationTokenDto(
                        username = username,
                        token = fcmToken
                    )
                )
            }catch (e: Exception){
                Log.e(TAG, "error on updateUserNotificationToken ${e.message}")
            }
        }
    }

    override suspend fun updateUserProfilePicture(storageDto: StorageDto, updatedProfileImage: (updated: Boolean)->Unit) {
        val token = prefs.getString("jwt", null)
        if(token != null){
            try{
                apiUsers.updateUserProfilePicture(
                    "Bearer $token",
                    storageDto
                )
                updatedProfileImage(true)
                Log.d(TAG, "update feito com sucesso")

            }catch (e: Exception){
                updatedProfileImage(false)
                Log.e(TAG, "error on updateUserProfilePicture ${e.message}")

            }
        }
    }


}