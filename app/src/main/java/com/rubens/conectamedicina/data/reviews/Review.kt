package com.rubens.conectamedicina.data.reviews

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val userUsername: String,
    val doctorUsername: String,
    val rating: Int,
    val feedbackText: String,
    val name: String,
    val timestamp: String
)
