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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rubens.conectamedicina.data.auth.AuthResult
import com.rubens.conectamedicina.ui.navigation.BottomNavItem
import com.rubens.conectamedicina.ui.signinScreen.viewModels.AuthenticationViewModel

@Composable
fun SignInScreen(viewModel: AuthenticationViewModel,
                 changeDestinationAfterLogin: ()->Unit,
                 goToSignUpScreen: ()->Unit,
                 snackbarHostState: SnackbarHostState) {




    LaunchedEffect(Unit){
            viewModel.signInResult.collect{
                    authResult->
                when(authResult){
                    is AuthResult.Authorized ->{
                        changeDestinationAfterLogin()
                    }
                    is AuthResult.Unauthorized ->{
                        snackbarHostState.showSnackbar(message = "You're not authorized! Check your login credentials!")

                    }
                    is AuthResult.UnknownError ->{
                        snackbarHostState.showSnackbar(message = "An unknown error ocurred! Try again or contact support!")
                    }
                }
            }




    }

    Column(modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 40.dp, vertical = 20.dp)) {
        Text("Sign In", modifier = Modifier.align(Alignment.CenterHorizontally)
            .padding(vertical = 20.dp),
            fontSize = 26.sp)
        EmailEditText(modifier = Modifier.fillMaxWidth()){
                typedUsername->
            viewModel.onEvent(AuthUiEvent.SignInUsernameChanged(typedUsername))
        }
        Spacer(Modifier.padding(top = 20.dp))
        PasswordEditText(modifier = Modifier.fillMaxWidth()){
                typedPassword->
            viewModel.onEvent(AuthUiEvent.SignInPasswordChanged(typedPassword))
        }

        Spacer(Modifier.padding(top = 20.dp))

        Button(onClick = {
                         viewModel.onEvent(AuthUiEvent.SignIn)
        },//onClick(typedUsername, typedPassword)},
            modifier = Modifier.fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#43c2ff")))){
            Text(text = "Sign In", color = androidx.compose.ui.graphics.Color.White)
        }

        Spacer(Modifier.height(20.dp))

        Text("Don't have an account? Sign Up!", modifier = Modifier.clickable {
            goToSignUpScreen()

        }
            .fillMaxWidth(1f),
            textAlign = TextAlign.Center)


    }

}