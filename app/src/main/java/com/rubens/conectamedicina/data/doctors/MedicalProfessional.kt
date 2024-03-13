package com.rubens.conectamedicina.data.doctors

data class MedicalProfessional(
    val professionalName: String,
    val professionalPicture: String? = null,
    val professionalSpecialty: String,
    val professionalLocation: String? = null,
    val professionalRating: Int = 1
)
