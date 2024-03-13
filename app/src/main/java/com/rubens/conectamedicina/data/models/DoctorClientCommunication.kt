package com.rubens.conectamedicina.data.models

import kotlinx.serialization.Serializable


@Serializable
data class DoctorClientCommunication(
    val idDoutor: String,
    val idClient: String,
    val idChat: String,
    val message: String
)

//todo adicionar a data hora para saber que horas a mensagem foi enviada
