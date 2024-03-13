package com.rubens.conectamedicina.data.doctors

import android.content.SharedPreferences
import android.util.Log


class DoctorDataSourceImpl(
    private val apiDoctors: ApiDoctors,
    private val prefs: SharedPreferences
): DoctorDataSource {

    private val TAG = "DoctorDataSourceImpl"


    override suspend fun getDoctorByUsername(username: String): Doctor? {
        return try {
            val token = prefs.getString("jwt", null)?: return null

            apiDoctors.getDoctorByUsername("Bearer $token", username)
        }catch (e: Exception){
            Log.e(TAG, "Error on getDoctorByUsername ${e.message}")
            null

        }
    }

    override suspend fun getAllDoctors(): AllDoctors? {
        return try{
            val token = prefs.getString("jwt", null)?: return null

            apiDoctors.getAllDoctors("Bearer $token")
        }catch (e: Exception){
            Log.e(TAG, "Error on getAllDoctors ${e.message}")
            null
        }
    }


}