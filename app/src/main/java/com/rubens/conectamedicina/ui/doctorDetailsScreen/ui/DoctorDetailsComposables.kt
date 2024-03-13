package com.rubens.conectamedicina.ui.doctorDetailsScreen.ui

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rubens.conectamedicina.CircularImageWithBackground
import com.rubens.conectamedicina.R
import com.rubens.conectamedicina.data.appointments.Appointment
import com.rubens.conectamedicina.data.doctors.Doctor
import com.rubens.conectamedicina.data.reviews.Review
import com.rubens.conectamedicina.ui.doctorDetailsScreen.viewModel.DoctorDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DoctorHeader(modifier: Modifier = Modifier,
                 @DrawableRes doctorImage: Int, doctor: Doctor?,
                 goBackToHomeScreen: ()->Unit){
    Box(modifier = modifier.fillMaxWidth()
        //.systemBarsPadding()
    ){
        Image(modifier = Modifier.height(200.dp)
            .fillMaxWidth(),
            contentScale = ContentScale.Crop,
            painter = if (doctor != null) {
                    if (doctor.doctorCoverPhotoUrl != ""){
                        rememberAsyncImagePainter(doctor.doctorCoverPhotoUrl)
                    }else{
                        painterResource(doctorImage)

                    }

            }else{
                painterResource(doctorImage)

            },
            contentDescription = null)

        Box(
            modifier = Modifier.padding(top = 40.dp, start = 16.dp)
        ){
            FloatingActionButton(
                onClick = {
                    goBackToHomeScreen()


                },
                shape= CircleShape,
                modifier = modifier
                    .size(48.dp),
                //.padding(start = 16.dp, top = 40.dp),
                backgroundColor = Color(android.graphics.Color.parseColor("#43c2ff"))
            ){
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "voltar para tela principal",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }



    }
}

@Composable
fun DoctorDetailsBody(
    modifier: Modifier = Modifier,
    goToChatScreen: (String, String) -> Unit,
    goToReviewScreen: (String) -> Unit,
    goToNewAppointment: (String, String, String, String, String, String) -> Unit,
    doctor: Doctor,
    userUsername: String,
    viewModel: DoctorDetailViewModel,
    userPhoto: String
){

    val appointmentsList = remember{ mutableStateOf<List<Appointment>?>(null) }

    val reviewsList = remember{ mutableStateOf<List<Review>>(listOf()) }

    LaunchedEffect(Unit){
        withContext(Dispatchers.IO){
            viewModel.getAllReviewsByDoctorUsername(doctor.username)

        }
    }

    LaunchedEffect(Unit){
        viewModel.reviewsResult.collect{
            if(it.isNotEmpty()){
                reviewsList.value = it
            }
        }
    }



    LaunchedEffect(Unit){
        withContext(Dispatchers.IO){
            Log.d("DoctorDetailsBody", "entrei aqui para chamar o metodo de pegar as appointments")
            viewModel.getDoctorAppointmentsByDoctorUsername(doctor.username)
        }
    }

    LaunchedEffect(Unit){
        viewModel.appointmentsResult.collect{
                appointments->
            appointmentsList.value = appointments
        }

    }

    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
            .background(Color.White)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())


    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(end = 40.dp)){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {


                Text(doctor?.name?.capitalize(Locale.current)?:"",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold)


                Text(doctor?.specialty?:"",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal,
                color = Color(android.graphics.Color.parseColor("#666666")))


                Row {
                    Image(imageVector = Icons.Default.LocationOn,
                        colorFilter = ColorFilter.tint(Color(android.graphics.Color.parseColor("#43c2ff"))),
                    contentDescription = null)
                    Text(style = MaterialTheme.typography.body1,
                    text = "${doctor?.city}, ${doctor?.stateCounty}",
                    modifier = Modifier.padding(top = 2.dp),
                    fontWeight = FontWeight.Normal,
                    color = Color(android.graphics.Color.parseColor("#666666")))
                }
            }
            Row(modifier = Modifier.padding(top = 45.dp, start = 16.dp)){
                CircularChatButton{
                    goToChatScreen(doctor.username, userUsername)
                }
                CircularReviewButton{
                    goToReviewScreen(doctor.username)


                }
            }


        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 36.dp)
        ) {

            if(appointmentsList.value != null){
                DetailBox(
                    modifier = Modifier.padding(top = 20.dp)
                        .weight(1f),
                    icon = R.drawable.all_appointments,
                    title = "Total\nAppointments",
                    value = appointmentsList.value!!.size.toString()
                )

                Spacer(Modifier.width(10.dp))

                DetailBox(
                    modifier = Modifier.padding(top = 20.dp)
                        .weight(1f),
                    icon = R.drawable.all_patients,
                    title = "Total\nPatients",
                    value = appointmentsList.value!!.distinctBy { it.clientId }.count().toString()
                )

                Spacer(Modifier.width(10.dp))


                DetailBox(
                    modifier = Modifier.padding(top = 20.dp)
                        .weight(1f),
                    icon = R.drawable.patients_waiting,
                    title = "Patients\nWaiting",
                    value = appointmentsList.value!!.count { app->  app.confirmacaoAtendimento == "aguardando confirmacao"}.toString()
                )
            }

        }

        Spacer(Modifier.height(20.dp))

        DetailLabelText("About",
            modifier = Modifier.padding(start = 16.dp)
        )
        DetailText(modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
            text = doctor.aboutDoctor?:"")

        Spacer(Modifier.height(20.dp))

        DetailLabelText("Availability", modifier = Modifier.padding(start = 16.dp))
        DetailText(modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
        text = if(doctor.availabilityTime != "")doctor.availabilityTime else "-")

        Spacer(Modifier.height(20.dp))

        DetailLabelText("Reviews", modifier = Modifier.padding(start = 16.dp))

        if(reviewsList.value.isNotEmpty()){
            Log.d("DoctorDetailsComposables", "reviews list nao esta vazia: $reviewsList")
            LazyRow(contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp)) {
                items(reviewsList.value){
                    ReviewAndFeedbackItem(review = it, viewModel = viewModel)
                }
            }


        }

//        ReviewsList(modifier = Modifier.padding(top = 8.dp),reviewsList = listOf(
//            R.drawable.doctor1,
//            R.drawable.doctor1,
//            R.drawable.doctor1,
//            R.drawable.doctor1,
//            R.drawable.doctor1
//        ))

        //Spacer(Modifier.height(10.dp))


        Button(onClick = {
            goToNewAppointment(doctor.name, doctor.specialty, userUsername, userUsername, doctor.username, userPhoto)

        },
            modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
            contentPadding = PaddingValues(vertical = 20.dp),
            colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#43c2ff")))){
            Text(text = "Book an Appointment",
                color = Color.White,
            fontSize = 20.sp)
        }

        //Spacer(Modifier.height(20.dp))









    }
}

@Composable
fun DetailBox(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    value: String
){

    Column(modifier,
    horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, fontWeight = FontWeight.Normal,
            color = Color(android.graphics.Color.parseColor("#666666")),
        fontSize = 16.sp,
        minLines = 2)
        Spacer(Modifier.height(5.dp))
        Row {
            Image(painter = painterResource(icon),
                colorFilter = ColorFilter.tint(Color(android.graphics.Color.parseColor("#43c2ff"))),
            contentDescription = null,
            modifier = Modifier.padding(top = 3.dp)
                .size(24.dp))
            Text(value,
                fontSize = 20.sp,
            modifier = Modifier.padding(start = 5.dp))
        }
    }



}

@Composable
fun DetailLabelText(text: String, modifier: Modifier = Modifier){
    Text(modifier = modifier, text = text,
    style = MaterialTheme.typography.h6,
    fontWeight = FontWeight.Bold)
}

@Composable
fun DetailText(text: String, modifier: Modifier = Modifier){
    Text(modifier = modifier,
        text = text,
        color = Color(android.graphics.Color.parseColor("#666666")),
    fontSize = 18.sp,
    //fontWeight = FontWeight.Light
    )
}

@Composable
fun ReviewsList(modifier: Modifier = Modifier,  @DrawableRes reviewsList: List<Int>){
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(reviewsList){
            review->
            CircularImageWithBackground(modifier = Modifier.size(36.dp), imageResId = review)

        }
    }
}


@Preview
@Composable
fun DoctorHeaderPreview(){
    //DoctorHeader(doctorImage = R.drawable.doctor1, doctor = singleDoctor.value)
}

@Preview
@Composable
fun DoctorBodyPreview(){
    //DoctorDetailsBody()
}

@Composable
fun CircularChatButton(modifier: Modifier = Modifier, btnClick: ()-> Unit){

    FloatingActionButton(
        onClick = {
                  btnClick()


        },
        modifier = modifier
            .size(66.dp)
            .padding(12.dp),
        backgroundColor = Color(android.graphics.Color.parseColor("#43c2ff"))
    ){
        Image(
            painter = painterResource(id = R.drawable.baseline_chat_bubble_24),
            colorFilter = ColorFilter.tint(Color.White),
            contentDescription = null,
            modifier = Modifier.size(22.dp)
        )
    }



}

@Composable
fun CircularReviewButton(modifier: Modifier = Modifier, btnClick: ()-> Unit){

    FloatingActionButton(
        onClick = {
            btnClick()


        },
        modifier = modifier
            .size(66.dp)
            .padding(12.dp),
        backgroundColor = Color(android.graphics.Color.parseColor("#43c2ff"))
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_star_filled),
            colorFilter = ColorFilter.tint(Color.White),
            contentDescription = null,
            modifier = Modifier.size(22.dp)
        )
    }



}


@Composable
fun UserReviewProfilePicture(
    circleSize: Int,
    viewModel: DoctorDetailViewModel,
    userUsername: String,
    modifier: Modifier = Modifier
){
    val userProfilePicture = remember { mutableStateOf("") }

    LaunchedEffect(Unit){
        withContext(Dispatchers.IO){
            viewModel.getUserProfilePictureByUsername(userUsername)
        }
    }

    LaunchedEffect(Unit){
        viewModel.userProfilePhotoForReviewResults.collect{
            Log.d("DoctorDetailsComposables", "acabei de pegar a foto do user: $it")
            userProfilePicture.value = it
        }
    }

    if(userProfilePicture.value != ""){
        AsyncImage(
            model = userProfilePicture.value?:R.drawable.baseline_person_24,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(circleSize.dp)
                .clip(CircleShape)
        )
    }
}


@Composable
fun ReviewAndFeedbackItem(review: Review, viewModel: DoctorDetailViewModel) {
    Row(modifier = Modifier.padding(vertical = 20.dp)){
        // Image
        UserReviewProfilePicture(circleSize = 42, viewModel, review.userUsername)
        Spacer(Modifier.width(10.dp))
    }

}

@Composable
fun TransparentSystemBars(){
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons){
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )

        onDispose {  }
    }
}

