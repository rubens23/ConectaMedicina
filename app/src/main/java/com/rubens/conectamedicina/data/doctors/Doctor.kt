package com.rubens.conectamedicina.data.doctors

import kotlinx.serialization.Serializable

@Serializable
data class Doctor(
    val username: String,
    val name: String,
    val profilePicture: String?,
    val specialty: String,
    val aboutDoctor: String,
    val lastName: String,
    val phoneNumber: String,
    val city: String,
    val postcode: String,
    val emailAddress: String,
    val stateCounty: String,
    val country: String,
    val availabilityTime: String,
    val rating: Int = 0,  // This represents the total of stars the doctor has
    val totalOfRatings: Int = 0,
    val doctorCoverPhotoUrl: String = ""


)
