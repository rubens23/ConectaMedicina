package com.rubens.conectamedicina.data.doctors

import kotlinx.serialization.Serializable

@Serializable
data class AllDoctors(
    val doctors: List<Doctor>
)
