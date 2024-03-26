package com.rubens.conectamedicina.data.auth

import com.rubens.conectamedicina.data.logging.LogManager
import retrofit2.HttpException


class UserAuthRepositoryImpl(
    private val api: UserAuthApi,
    private val authTokenManager: AuthTokenManager,
    private val logManager: LogManager
): AuthRepository {

    private val tag = "UserAuthRepositoryImpl"
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
            logManager.printErrorLogs(tag, "error on signUp: ${e.message}")
            if (e.code() == 401){
                logManager.printErrorLogs(tag,"error 401 on signUp: ${e.message}")

                AuthResult.Unauthorized()
            }else{
                logManager.printErrorLogs(tag,"error on signUp: ${e.message}")

                AuthResult.UnknownError()
            }
        }catch (e: Exception){
            logManager.printErrorLogs(tag,"error on signUp: ${e.message}")

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

            authTokenManager.saveNewToken(response.token)
            authTokenManager.saveNewUsername(username)
            AuthResult.Authorized()



        }catch (e: HttpException){
            if (e.code() == 401){
                logManager.printErrorLogs(tag,"error 401 on signIn: ${e.message}")

                AuthResult.Unauthorized()
            }else{
                logManager.printErrorLogs(tag,"error on signIn: ${e.message}")

                AuthResult.UnknownError()
            }
        }catch (e: Exception){
            logManager.printErrorLogs(tag,"error on signIn: ${e.message}")

            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = authTokenManager.getToken()?: return AuthResult.Unauthorized()
            api.authenticate("Bearer $token")



            AuthResult.Authorized()

        }catch (e: HttpException){
            if (e.code() == 401){
                logManager.printErrorLogs(tag,"error 401 on authenticate: ${e.message}")

                AuthResult.Unauthorized()
            }else{
                logManager.printErrorLogs(tag,"error on authenticate: ${e.message}")

                AuthResult.UnknownError()
            }
        }catch (e: Exception){
            logManager.printErrorLogs(tag,"error on authenticate: ${e.message}")

            AuthResult.UnknownError()
        }
    }

    override suspend fun getUserSecret(): String? {
        return try{
            val token = authTokenManager.getToken()?: return null
            val response = api.getUserSecret("Bearer $token")
            response.secret
        }catch (e: Exception){
            logManager.printErrorLogs(tag,"error on getUserSecret: ${e.message}")

            return null
        }

    }




}