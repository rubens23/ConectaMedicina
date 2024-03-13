package com.rubens.conectamedicina.data.auth

import com.rubens.conectamedicina.data.models.MongoDbConnection

interface AuthRepository {
    suspend fun signUp(
        username: String,
        password: String,
        name: String,
        lastName: String
    ): AuthResult<Unit>

    suspend fun signIn(username: String, password: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
    suspend fun getUserSecret(): String? = ""
}