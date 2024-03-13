package com.rubens.conectamedicina.data.appointments

import kotlinx.serialization.Serializable

@Serializable
data class Appointment(
    val day: String,
    val month: String,
    val doctor: String,
    val hour: String,
    val service: String,
    val clientName: String,
    val clientId: String?,
    val doctorId: String,
    val appointmentDayFormatted: String,
    val confirmacaoAtendimento: String = "aguardando confirmacao",
    val clientPhotoUrl: String = ""

)
