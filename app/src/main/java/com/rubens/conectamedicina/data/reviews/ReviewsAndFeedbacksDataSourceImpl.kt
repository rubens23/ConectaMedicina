package com.rubens.conectamedicina.data.reviews

import android.content.SharedPreferences
import android.util.Log

class ReviewsAndFeedbacksDataSourceImpl(
    private val apiReviews: ApiReviews,
    private val prefs: SharedPreferences
): ReviewsAndFeedbacksDataSource {
    private val TAG = "ReviewsAndFeedbacksDataSourceImpl"


    override suspend fun saveNewReview(review: Review): Boolean {
        val token = prefs.getString("jwt", null)?: false
        return try{
            apiReviews.saveNewReview("Bearer $token", review)
            true


        }catch (e: Exception){
            Log.e(TAG, "error on saveNewReview: ${e.message}")
            false
        }

    }

    override suspend fun getAllReviewsByDoctorUsername(doctorUsername: String): List<Review>? {
        return try {
            apiReviews.getAllReviewsByDoctorUsername(doctorUsername)
        }catch (e: Exception){
            Log.e(TAG, "error on getAllReviewsByDoctorUsername: ${e.message}")
            null

        }
    }

}