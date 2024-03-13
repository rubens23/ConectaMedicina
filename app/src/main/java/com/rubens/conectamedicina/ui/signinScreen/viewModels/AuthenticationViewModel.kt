package com.rubens.conectamedicina.ui.signinScreen.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubens.conectamedicina.data.auth.AuthRepository
import com.rubens.conectamedicina.data.auth.AuthResult
import com.rubens.conectamedicina.data.auth.di.UserAuthRepositoryQualifier
import com.rubens.conectamedicina.ui.signinScreen.data.auth.AuthState
import com.rubens.conectamedicina.ui.signinScreen.ui.AuthUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    @UserAuthRepositoryQualifier private val userAuthRepository: AuthRepository,
    ): ViewModel() {



    private var signInState by mutableStateOf(AuthState())
    private var signUpState by mutableStateOf(AuthState())

    private val _signInChannel = Channel<AuthResult<Unit>>()
    val signInResult get() = _signInChannel.receiveAsFlow()

    private val _signUpChannel = Channel<AuthResult<Unit>>()
    val signUpResult get() = _signUpChannel.receiveAsFlow()


    fun onEvent(event: AuthUiEvent){
        when(event){
            is AuthUiEvent.SignInUsernameChanged -> {
                signInState = signInState.copy(signInUsername = event.value)
            }
            is AuthUiEvent.SignInPasswordChanged -> {
                signInState = signInState.copy(signInPassword = event.value)

            }
            is AuthUiEvent.SignIn ->{
                signIn()
            }
            is AuthUiEvent.SignUpUsernameChanged ->{
                signUpState = signUpState.copy(signUpUsername = event.value)

            }
            is AuthUiEvent.SignUpPasswordChanged ->{
                signUpState = signUpState.copy(signUpPassword = event.value)

            }
            is AuthUiEvent.SignUpNameChanged ->{
                signUpState = signUpState.copy(signUpName = event.value)

            }
            is AuthUiEvent.SignUpLastNameChanged ->{
                signUpState = signUpState.copy(signUpLastname = event.value)

            }
            is AuthUiEvent.SignUp ->{
                signUp()

            }
        }
    }

    private fun signIn(){
        viewModelScope.launch {
            signInState = signInState.copy(isLoading = true)
            val result = userAuthRepository.signIn(
                username = signInState.signInUsername,
                password = signInState.signInPassword
            )
            _signInChannel.send(result)
            signInState = signInState.copy(isLoading = false)

        }
    }

    private fun signUp(){
        viewModelScope.launch{

            signUpState = signUpState.copy(isLoading = true)
            val result = userAuthRepository.signUp(
                username = signUpState.signUpUsername,
                password = signUpState.signUpPassword,
                name = signUpState.signUpName,
                lastName = signUpState.signUpLastname
            )
            _signUpChannel.send(result)
            signUpState = signUpState.copy(isLoading = false)





        }
    }
}