package com.rubens.conectamedicina.data.auth

import android.content.SharedPreferences
import retrofit2.HttpException


class ClientAuthRepositoryImpl(
    private val api: ClientAuthApi,
    private val prefs: SharedPreferences
): AuthRepository {
    override suspend fun signUp(username: String,
                                password: String,
    name: String,
    lastName: String): AuthResult<Unit> {
        return try {
            api.signUp(
                request = SignUpRequest(
                    username = username,
                    password = password,
                    name = name,
                    lastName = lastName
                )
            )
            signIn(username, password)

        }catch (e: HttpException){
            //todo
            e
            if (e.code() == 401){
                AuthResult.Unauthorized()
            }else{
                AuthResult.UnknownError()
            }
        }catch (e: Exception){
            //todo
            e
            AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(username: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.signIn(
                request = SignInRequest(
                    username = username,
                    password = password
                )
            )

            prefs.edit()
                .putString("jwt", response.token)
                .apply()

            prefs.edit()
                .putString("username", username)
                .apply()


            AuthResult.Authorized()



        }catch (e: HttpException){
            //todo
            if (e.code() == 401){
                AuthResult.Unauthorized()
            }else{
                e
                AuthResult.UnknownError()
            }
        }catch (e: Exception){
            //todo
            e
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null)?: return AuthResult.Unauthorized()
            api.authenticate("Bearer $token")



            AuthResult.Authorized()

        }catch (e: HttpException){
            //todo
            if (e.code() == 401){
                AuthResult.Unauthorized()
            }else{
                AuthResult.UnknownError()
            }
        }catch (e: Exception){
            AuthResult.UnknownError()
        }
    }

    override suspend fun getUserSecret(): String? {
        return try{
            val token = prefs.getString("jwt", null)?: return null
            val response = api.getUserSecret("Bearer $token")
            response.secret
        }catch (e: Exception){
            //todo
            e
            println(e.message.toString())
            return null
        }

    }


}