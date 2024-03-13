package com.rubens.conectamedicina.data.storage

import kotlinx.serialization.Serializable

@Serializable
data class StorageDto(
    val imgLink: String,
    val userUsername: String
)

