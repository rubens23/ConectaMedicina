package com.rubens.conectamedicina.data.user

import com.rubens.conectamedicina.data.storage.StorageDto

interface UserDataSource {

    suspend fun getUserByUserId(userId: String): User?
    suspend fun updateUserNotificationToken(fcmToken: String, username: String)
    suspend fun updateUserProfilePicture(storageDto: StorageDto)
    suspend fun getUserProfilePictureByUsername(userUsername: String): String


}