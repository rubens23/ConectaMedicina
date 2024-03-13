package com.rubens.conectamedicina.data.appointments

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiAppointments {

    @POST("saveNewAppointment")
    suspend fun saveNewAppointment(
        @Header("Authorization") token: String,
        @Body newAppointment: Appointment
    )

    @GET("getUserAppointments")
    suspend fun getUserAppointments(
        @Header("Authorization") token: String,
        @Query("username") username: String
    ): List<Appointment>?

    @GET("appointmentsByDoctorUsername")
    suspend fun getDoctorAppointments(
        @Header("Authorization") token: String,
        @Query("doctorUsername") doctorUsername: String
    ): List<Appointment>?
}