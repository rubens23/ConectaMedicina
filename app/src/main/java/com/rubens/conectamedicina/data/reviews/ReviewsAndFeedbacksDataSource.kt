package com.rubens.conectamedicina.data.reviews

interface ReviewsAndFeedbacksDataSource {
    suspend fun saveNewReview(review: Review): Boolean
    suspend fun getAllReviewsByDoctorUsername(doctorUsername: String): List<Review>?
}