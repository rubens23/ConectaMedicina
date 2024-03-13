package com.rubens.conectamedicina.data.auth

data class SignUpRequest(
    val username: String,
    val password: String,
    val name: String,
    val lastName: String
)
