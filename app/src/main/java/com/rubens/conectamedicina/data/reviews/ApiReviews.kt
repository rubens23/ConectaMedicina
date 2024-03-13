package com.rubens.conectamedicina.data.reviews

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiReviews {

    @POST("saveNewReview")
    suspend fun saveNewReview(
        @Header("Authorization") token: String,
        @Body review: Review)

    @GET("reviewsByDoctorId")
    suspend fun getAllReviewsByDoctorUsername(
        @Query("doctorId") doctorUsername: String
    ): List<Review>?
}