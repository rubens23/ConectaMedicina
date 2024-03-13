package com.rubens.conectamedicina.ui.doctorDetailsScreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rubens.conectamedicina.R
import com.rubens.conectamedicina.ui.doctorDetailsScreen.viewModel.DoctorDetailViewModel

@Composable
fun DoctorsDetailScreenLayout(
    goToChatScreen: (String, String)-> Unit,
    goToReviewScreen: (String)-> Unit,
    goToNewAppointmentScreen: (String, String, String, String, String, String) -> Unit,
    goBackToHomeScreen: ()-> Unit,
    doctorUsername: String,
    viewModel: DoctorDetailViewModel,
    userUsername: String,
    snackbarHostState: SnackbarHostState,
    userPhotoUrl: String
){

    val singleDoctor by remember { viewModel.doctorResult }
    val errorGettingDoctor by remember { viewModel.errorGettingDoctor }


        viewModel.getDoctorByUsername(doctorUsername)






    if(errorGettingDoctor != ""){
        LaunchedEffect(Unit){
            snackbarHostState.showSnackbar(message = errorGettingDoctor,
                actionLabel = "Ok")
        }
    }






    Column(modifier = Modifier//.systemBarsPadding()
    ) {
        if(singleDoctor != null){
            DoctorHeader(doctorImage = R.drawable.cover_placeholder,
                doctor = singleDoctor,
                goBackToHomeScreen = {goBackToHomeScreen()})
            DoctorDetailsBody(
                goToChatScreen = {doctorUsername, userUsername->
                                 goToChatScreen(doctorUsername, userUsername)
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
                userPhoto = userPhotoUrl)
        }

    }
}

@Preview
@Composable
fun DoctorsDetailScreenLayoutPreview(){
    //DoctorsDetailScreenLayout()
}