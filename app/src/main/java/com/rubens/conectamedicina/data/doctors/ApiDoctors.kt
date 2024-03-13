package com.rubens.conectamedicina.data.doctors

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiDoctors {


    @GET("alldoctors")
    suspend fun getAllDoctors(
        @Header("Authorization") token: String
    ): AllDoctors?

    @GET("doctor")
    suspend fun getDoctorByUsername(
        @Header("Authorization")token: String,
        @Query("username") username: String): Doctor?
}