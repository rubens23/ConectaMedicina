package com.rubens.conectamedicina.data.appointments

interface AppoitmentsDataSource {
    suspend fun pegarTodosAppointments(username: String): List<Appointment>?

    suspend fun saveNewAppointment(appointment: Appointment): Boolean
    suspend fun getDoctorAppointmentsByDoctorUsername(doctorUsername: String): List<Appointment>?


}