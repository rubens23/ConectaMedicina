package com.rubens.conectamedicina.ui.doctorDetailsScreen.ui

import android.util.Log
import android.view.WindowInsetsController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import com.rubens.conectamedicina.R
import com.rubens.conectamedicina.ui.doctorDetailsScreen.viewModel.DoctorDetailViewModel

@Composable
fun DoctorsDetailScreenLayout(
    goToChatScreen: (String, String, String, String, String)-> Unit,
    goToReviewScreen: (String)-> Unit,
    goToNewAppointmentScreen: (String, String, String, String, String, String) -> Unit,
    goBackToHomeScreen: ()-> Unit,
    doctorUsername: String,
    viewModel: DoctorDetailViewModel,
    userUsername: String,
    snackbarHostState: SnackbarHostState,
    userPhotoUrl: String){

    val singleDoctor by remember { viewModel.doctorResult }
    val errorGettingDoctor by remember { viewModel.errorGettingDoctor }

    Log.d("solvingPhotos2", "userPhoto here in DoctorsDetailScreenLayout $userPhotoUrl")


        viewModel.getDoctorByUsername(doctorUsername)






    if(errorGettingDoctor != ""){
        LaunchedEffect(Unit){
            snackbarHostState.showSnackbar(message = errorGettingDoctor,
                actionLabel = "Ok")
        }
    }







    val navigationBarHeight = WindowInsets.systemBars.asPaddingValues().calculateTopPadding() + WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()

    Log.d("insetsControl", "$navigationBarHeight")


    Scaffold(
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, navigationBarHeight),
    ){
        paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
        ){
            if(singleDoctor != null){
                val docPhoto = singleDoctor!!.profilePicture?: ""
                DoctorHeader(doctorImage = R.drawable.cover_placeholder,
                    doctor = singleDoctor,
                    goBackToHomeScreen = {goBackToHomeScreen()})
                DoctorDetailsBody(
                    goToChatScreen = {
                        Log.d("solvingSpace7", "userPhotoUrl aqui no doctorDetails tem o valor: $userPhotoUrl")
                        goToChatScreen(doctorUsername, userUsername, userPhotoUrl, docPhoto, singleDoctor!!.name)
                    },
                    goToReviewScreen = {doctorUsername->
                        goToReviewScreen(doctorUsername)
                    },
                    goToNewAppointment = {doctorName, doctorSpecialty, clientName, clientId, doctorUsername, clientPhotoUrl ->
                        goToNewAppointmentScreen(doctorName, doctorSpecialty, clientName, clientId, doctorUsername, clientPhotoUrl)
                    },
                    doctor = singleDoctor!!,
                    userUsername = userUsername,
                    viewModel = viewModel,
                    userPhoto = userPhotoUrl,
                    doctorPhoto = docPhoto)
            }

        }

    }

}

@Preview
@Composable
fun DoctorsDetailScreenLayoutPreview(){
    //DoctorsDetailScreenLayout()
}