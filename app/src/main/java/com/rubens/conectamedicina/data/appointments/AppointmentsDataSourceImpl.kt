package com.rubens.conectamedicina.data.appointments

import android.content.SharedPreferences
import android.util.Log

class AppointmentsDataSourceImpl(
    private val apiAppointments: ApiAppointments,
    private val prefs: SharedPreferences
): AppoitmentsDataSource {

    private val TAG = "AppointmentsDataSourceImpl"

    override suspend fun pegarTodosAppointments(username: String): List<Appointment>?{
        val token = prefs.getString("jwt", null)?: return null
        return try{
            apiAppointments.getUserAppointments("Bearer $token", username)
        }catch (e: Exception){
            Log.e(TAG, "error on pegarTodosAppointments: ${e.message}")
            null
        }

    }

    override suspend fun saveNewAppointment(
        appointment: Appointment
    ): Boolean {
        val token = prefs.getString("jwt", null)?: return false
        return try{
            apiAppointments.saveNewAppointment("Bearer $token", appointment)
            true
        }catch (e: Exception){
            Log.e(TAG, "error on saveNewAppointment: ${e.message}")
            false
        }

    }

    override suspend fun getDoctorAppointmentsByDoctorUsername(doctorUsername: String): List<Appointment>? {
        val token = prefs.getString("jwt", null)
        return if(token != null){
            try{
                val appointments = apiAppointments.getDoctorAppointments("Bearer $token", doctorUsername)
                Log.d(TAG, "doctorAppointments response: $appointments")
                appointments

            }catch (e: Exception){
                Log.e(TAG, "error on getDoctorAppointmentsByDoctorUsername: ${e.message}")
                null

            }
        }else{
            null
        }
    }
}

//todo fazer a parte do endpoint la no server para salvar appointments e retornar appointments