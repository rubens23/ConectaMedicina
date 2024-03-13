package com.rubens.conectamedicina.data.doctors

interface DoctorDataSource {

    suspend fun getDoctorByUsername(username: String): Doctor?
    suspend fun getAllDoctors(): AllDoctors?
}