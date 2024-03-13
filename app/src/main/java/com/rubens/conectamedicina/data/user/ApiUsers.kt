package com.rubens.conectamedicina.data.user

import com.rubens.conectamedicina.data.notification.UpdateUserNotificationTokenDto
import com.rubens.conectamedicina.data.reviews.UserReviewPhoto
import com.rubens.conectamedicina.data.storage.StorageDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiUsers {
    @GET("userById")
    suspend fun getUserById(
        @Header("Authorization") token: String,
        @Query("userId") userId: String
    ): User?

    @POST("updateUserNotificationToken")
    suspend fun updateUserFcmToken(
        @Header("Authorization") token: String,
        @Body tokenUpdate: UpdateUserNotificationTokenDto
    )

    @POST("updateUserProfilePicture")
    suspend fun updateUserProfilePicture(
        @Header("Authorization") token: String,
        @Body storageDto: StorageDto
    )

    @GET("getUserProfilePicture")
    suspend fun getUserProfilePictureByUsername(
        @Query("userUsername") userUsername: String
    ): UserPhoto
}