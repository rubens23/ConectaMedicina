package com.rubens.conectamedicina.ui.signinScreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rubens.conectamedicina.data.auth.AuthResult
import com.rubens.conectamedicina.ui.MainViewModel
import com.rubens.conectamedicina.ui.navigation.BottomNavItem
import com.rubens.conectamedicina.ui.signinScreen.viewModels.AuthenticationViewModel


@Composable
fun SignUpScreen(viewModel: AuthenticationViewModel,
                 snackbarHostState: SnackbarHostState,
                 goToLoginScreen: ()->Unit
) {
    var username by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(Unit){
        viewModel.signUpResult.collect{
                authResult->
            when(authResult){
                is AuthResult.Authorized ->{
                    snackbarHostState.showSnackbar(message = "Your signup was successful! you can login now")



                }
                is AuthResult.Unauthorized ->{
                    snackbarHostState.showSnackbar(message = "An error ocurred. Try again and if the problem persists, contact support")

                }
                is AuthResult.UnknownError ->{
                    snackbarHostState.showSnackbar(message = "An error ocurred. Have you filled all fields?")
                }
            }
        }




    }


    Column(modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 40.dp, vertical = 20.dp)) {
        Text("Sign Up", modifier = Modifier.align(Alignment.CenterHorizontally)
            .padding(vertical = 20.dp),
            fontSize = 26.sp)
        EmailEditText(modifier = Modifier.fillMaxWidth()){
                typedUsername->
            username = typedUsername
            viewModel.onEvent(AuthUiEvent.SignUpUsernameChanged(username))
        }
        Spacer(Modifier.padding(top = 20.dp))

        NameEditText(modifier = Modifier.fillMaxWidth()){
                typedName->
            name = typedName
            viewModel.onEvent(AuthUiEvent.SignUpNameChanged(name))

        }
        Spacer(Modifier.padding(top = 20.dp))

        LastNameEditText(modifier = Modifier.fillMaxWidth()) {
                typedLastName->
            lastName = typedLastName
            viewModel.onEvent(AuthUiEvent.SignUpLastNameChanged(lastName))

        }
        Spacer(Modifier.padding(top = 20.dp))
        PasswordEditText(modifier = Modifier.fillMaxWidth()){
                typedPassword->
            password = typedPassword
            viewModel.onEvent(AuthUiEvent.SignUpPasswordChanged(password))
        }

        Spacer(Modifier.padding(top = 20.dp))

        //KindRadioGroupUsage(radioGroupItems = listOf("Client", "Professional"), viewModel = viewModel)

        Spacer(Modifier.padding(top = 20.dp))



        Button(onClick = {
                         viewModel.onEvent(AuthUiEvent.SignUp)
        },//onClick(typedUsername, typedPassword)},
            modifier =  Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#43c2ff")))){
            Text(text = "Sign Up", color = androidx.compose.ui.graphics.Color.White)
        }

        Spacer(Modifier.height(20.dp))

        Text(text = "already signed up? Log in!", modifier = Modifier.clickable {
            goToLoginScreen()

        }
            .fillMaxWidth(1f),
            textAlign = TextAlign.Center)


    }

}