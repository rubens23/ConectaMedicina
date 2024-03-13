package com.rubens.conectamedicina.ui.appointmentScreen

data class NewAppointmentState(
    val name: String = "",
    val age: String = "",
    val date: String = "",
    val visitTime: String = "",
    val condition: String = "",
    val description: String = "",
    val chosenHour: String = "",
    val chosenDay: String = "",
    val chosenMonth: String = "",
    val doctorName: String = "",
    val clientName: String = "",
    val service: String = "",
    val clientId: String = "",
    val doctorId: String = "",
    val appointmentDayFormatted: String = "",
    val userPhotoUrl: String = ""
)