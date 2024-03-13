package com.rubens.conectamedicina.ui.appointmentScreen.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextButton
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rubens.conectamedicina.data.appointments.Appointment
import com.rubens.conectamedicina.shimmertutorial.ShimmerAppointmentList
import com.rubens.conectamedicina.ui.appointmentScreen.viewModel.AppointmentsViewModel
import com.rubens.conectamedicina.ui.MainViewModel
import java.util.Locale


@Composable
fun AppointmentsListLayout(viewModel: AppointmentsViewModel,
                           snackbarHostState: SnackbarHostState){


    val appointmentList = remember{mutableStateOf<List<Appointment>?>(null)}
    val errorGettingAppointments by remember { viewModel.errorGettingAppointments }
    val appointmentsAreLoading by remember {viewModel.appointmentsAreLoading}

    LaunchedEffect(Unit){
        viewModel.appointmentsResult.collect{
            appointmentList.value = it
        }
    }

    if(errorGettingAppointments != ""){
        LaunchedEffect(Unit){
            viewModel.setAppointmentsAreLoading(false)
            snackbarHostState.showSnackbar(message = errorGettingAppointments,
                actionLabel = "Ok")
        }
    }


    Column(modifier = Modifier.padding(top = 16.dp)) {

        Text(
            text = "Appointments",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
        )


        if(appointmentList.value != null){
            ShimmerAppointmentList(
                isLoading = appointmentsAreLoading,
                contentAfterLoading = {LazyColumn {
                    items(appointmentList.value!!){
                            appointment->
                        AppointmentItem(appointment, viewModel)
                    }

                }
                }
            )

        }else{
            ShimmerAppointmentList(
                isLoading = appointmentsAreLoading,
                contentAfterLoading = {}
            )
        }


    }
}

@Composable
fun AppointmentItem(item: Appointment, viewModel: AppointmentsViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 0.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {
        Column(

        ) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Row {
                    Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row{
                            Text(
                                text = item.month,
                                fontSize = 16.sp,
                                color = Color(android.graphics.Color.parseColor("#666666"))
                            )


                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            item.day,
                            fontWeight = FontWeight.Bold,
                            fontSize = 48.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = Color(0xFF43C2FF)
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = viewModel.getDayOfTheWeekByDate(item.appointmentDayFormatted)
                                .uppercase(
                                    Locale.getDefault()
                                ),
                            fontSize = 16.sp,
                            letterSpacing = 2.sp,
                            color = Color(android.graphics.Color.parseColor("#666666"))
                        )

                    }
                    Spacer(Modifier.width(45.dp))
                    Column() {
                        Text(
                            text = "Timing",
                            fontSize = 15.sp,
                            color = Color(android.graphics.Color.parseColor("#666666"))
                        )

                            Text(
                                item.hour,
                                modifier = Modifier.padding(top = 2.dp),
                                fontSize = 22.sp
                            )
//                            Spacer(modifier = Modifier.width(5.dp))
//                            Image(
//                                painter = painterResource(R.drawable.baseline_access_time_24),
//                                contentDescription = null
//                            )
                        Spacer(Modifier.height(20.dp))
                        Text(
                            text = "Service",
                            fontSize = 15.sp,
                            color = Color(android.graphics.Color.parseColor("#666666"))
                        )

                        Text(
                            item.service,
                            modifier = Modifier.padding(top = 2.dp),
                            fontSize = 22.sp
                        )




                    }

                    Spacer(Modifier.width(20.dp))

                    Column {
                        Text(
                            text = "Doctor",
                            fontSize = 15.sp,
                            color = Color(android.graphics.Color.parseColor("#666666"))
                        )

                        Text(
                            item.doctor,
                            modifier = Modifier.padding(top = 2.dp),
                            fontSize = 22.sp
                        )
                    }

                }

            }
            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Gray))


        }


    }

}

@Preview
@Composable
fun AppointmentItemPreview() {
    val appointmentsList = listOf<Appointment>(
        Appointment(
            day = "3",
            month = "october",
            doctor = "Carlos",
            hour = "13:00",
            service = "cardilogyst",
            clientName = "Rubens",
            clientId = "rubens@teste.com",
            doctorId = "carlos@teste.com",
            appointmentDayFormatted = "ter"
        ),
        Appointment(
            day = "13",
            month = "october",
            doctor = "Carlos",
            hour = "13:00",
            service = "cardilogyst",
            clientName = "Rubens",
            clientId = "rubens@teste.com",
            doctorId = "carlos@teste.com",
            appointmentDayFormatted = "ter",
            confirmacaoAtendimento = ""
        )
    )
//    LazyColumn {
//        items(appointmentsList) { item ->
//            AppointmentItem(item)
//        }
//    }
}




@Composable
@Preview
fun NewAppointmentLayoutPreview() {
    //NewAppointmentLayout()
}

