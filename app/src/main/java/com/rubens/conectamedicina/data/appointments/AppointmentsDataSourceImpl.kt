package com.rubens.conectamedicina.data.appointments

import android.util.Log
import com.rubens.conectamedicina.data.auth.AuthTokenManager

class AppointmentsDataSourceImpl(
    private val apiAppointments: ApiAppointments,
    private val authTokenManager: AuthTokenManager
): AppoitmentsDataSource {

    private val tag = "AppointmentsDataSourceImpl"

    override suspend fun pegarTodosAppointments(username: String): List<Appointment>?{
        val token = authTokenManager.getToken()?: return null
        return try{
            apiAppointments.getUserAppointments("Bearer $token", username)
        }catch (e: Exception){
            Log.e(tag, "error on pegarTodosAppointments: ${e.message}")
            null
        }

    }

    override suspend fun saveNewAppointment(
        appointment: Appointment
    ): Boolean {
        val token = authTokenManager.getToken()?: return false
        return try{
            apiAppointments.saveNewAppointment("Bearer $token", appointment)
            true
        }catch (e: Exception){
            Log.e(tag, "error on saveNewAppointment: ${e.message}")
            false
        }

    }

    override suspend fun getDoctorAppointmentsByDoctorUsername(doctorUsername: String): List<Appointment>? {
        val token = authTokenManager.getToken()
        return if(token != null){
            try{
                val appointments = apiAppointments.getDoctorAppointments("Bearer $token", doctorUsername)
                Log.d(tag, "doctorAppointments response: $appointments")
                appointments

            }catch (e: Exception){
                Log.e(tag, "error on getDoctorAppointmentsByDoctorUsername: ${e.message}")
                null

            }
        }else{
            null
        }
    }
}

