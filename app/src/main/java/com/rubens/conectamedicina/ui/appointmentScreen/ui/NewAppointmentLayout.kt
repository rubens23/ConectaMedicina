package com.rubens.conectamedicina.ui.appointmentScreen.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rubens.conectamedicina.ui.appointmentScreen.NewAppointmentEvent
import com.rubens.conectamedicina.ui.appointmentScreen.viewModel.AppointmentsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewAppointmentLayout(
    viewModel: AppointmentsViewModel,
    doctorName: String,
    doctorService: String,
    clientName: String,
    clientId: String,
    doctorId: String,
    snackbarHostState: SnackbarHostState,
    clientPhotoUrl: String

) {



    var scrollState = rememberScrollState()
    var name by remember { viewModel.name}
    var age by remember { viewModel.age }
    var condition by remember { viewModel.condition }
    var description by remember { viewModel.description }
    var date by remember { viewModel.date }
    var visitTime by remember { viewModel.visitTime }






    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    val openDatePickerDialog = remember { mutableStateOf(false) }
    val openTimePickerDialog = remember { mutableStateOf(false) }

    var appointmentSaveStatus by remember { viewModel.appointmentSaveStatus }

    viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentDoctorNameChanged(doctorName))
    viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentServiceChanged(doctorService))
    viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentClientNameChanged(clientName))
    viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentClientIdChanged(clientId))
    viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentDoctorIdChanged(doctorId))
    viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentClientPhotoChanged(clientPhotoUrl))

    if(appointmentSaveStatus != ""){
        LaunchedEffect(Unit){
            snackbarHostState.showSnackbar(message = appointmentSaveStatus,
                actionLabel = "Ok")
        }
    }


    Column(modifier = Modifier
        .padding(16.dp)
        .verticalScroll(state = scrollState)) {
        Text(
            text = "New Appointment",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(vertical = 20.dp)
        )
        Text(
            text = "Name",
            fontWeight = FontWeight.Bold
        )
        //name textfield
        OutlinedTextField(
            value = name,
            onValueChange = { name = it
                            viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentNameChanged(it))},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF43C2FF),
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color(0xFF43C2FF)
            ),
            placeholder = { Text("Type your name") },
            modifier = Modifier.fillMaxWidth()


        )
        Spacer(modifier = Modifier.height(10.dp))
            Column {
                //age text field
                Text(
                    text = "Age",
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it
                                    viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentAgeChanged(it))},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF43C2FF),
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = Color(0xFF43C2FF)
                    ),
                    placeholder = { Text("age") },
                    modifier = Modifier.fillMaxWidth(0.2f)


                )


            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                //Date text field
                Text(
                    text = "Appointment Date",
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = viewModel.formatMillisecondsDateToDateFormat(datePickerState.selectedDateMillis),
                    onValueChange = { date = it
                                    viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentDateChanged(it))},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF43C2FF),
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = Color(0xFF43C2FF),
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,

                        ),
                    enabled = false,
                    placeholder = { Text("dd/MM/yyyy") },
                    modifier = Modifier.fillMaxWidth(0.4f)
                        .clickable {
                            Log.d("clickDialog", "cliquei aqui no date text field")
                            openDatePickerDialog.value = true
                        }



                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                //visit time text field
                Text(
                    text = "Appointment Time",
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = "${timePickerState.hour}:${if(timePickerState.minute == 0)"00" else timePickerState.minute}",
                    onValueChange = { visitTime = it
                                    viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentVisitTimeChanged(it))},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF43C2FF),
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = Color(0xFF43C2FF),
                        disabledTextColor = MaterialTheme.colorScheme.onSurface

                    ),
                    enabled = false,
                    placeholder = { Text("01:30 PM") },
                    modifier = Modifier.fillMaxWidth(0.6f)
                        .clickable {
                            openTimePickerDialog.value = true
                        }


                )
            }



        Spacer(modifier = Modifier.height(10.dp))
        //condition
        Text(
            text = "Condition",
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = condition,
            onValueChange = { condition = it
                            viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentConditionChanged(it))},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF43C2FF),
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color(0xFF43C2FF)
            ),
            placeholder = { Text("What's your condition") },
            modifier = Modifier.fillMaxWidth()


        )

        Spacer(modifier = Modifier.height(10.dp))
        //description
        Text(
            text = "What are you feeling?",
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it
                            viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentDescriptionChanged(it))},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF43C2FF),
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color(0xFF43C2FF)
            ),
            placeholder = { Text("In a few words describe what you're feeling") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 3


        )
        Spacer(modifier = Modifier.height(20.dp))

        //salvando com dados fake por enquanto
        Button(
            onClick = {
                viewModel.saveNewAppointment()
            },
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color(0xFF43C2FF))
        ) {

            Text(
                text = "Send Appointment Request",
                modifier = Modifier,
                color = Color.White
            )
        }


    }

    //DatePicker
    if(openDatePickerDialog.value){
        DatePickerDialog(
            onDismissRequest = {
                openDatePickerDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDatePickerDialog.value = false
                    }
                ){
                    Text(text = "OK")
                    viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentChosenDayChanged(viewModel.getDay(datePickerState.selectedDateMillis)))
                    viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentChosenMonthChanged(viewModel.getMonth(datePickerState.selectedDateMillis)))
                    viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentAppointmentDayFormattedChanged(viewModel.formatMillisecondsDateToDateFormat(datePickerState.selectedDateMillis)))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDatePickerDialog.value = false
                    }
                ){
                    Text(text = "CANCEL")
                }
            }
        ){
            DatePicker(
                state = datePickerState
            )
        }
    }

    //Time Picker


    if(openTimePickerDialog.value){
        DatePickerDialog(
            onDismissRequest = {
                openTimePickerDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openTimePickerDialog.value = false
                    }
                ){
                    Text(text = "OK")
                    viewModel.onAppointmentEvent(NewAppointmentEvent.AppointmentChosenHourChanged("${timePickerState.hour}:${if(timePickerState.minute == 0)"00" else timePickerState.minute}"))

                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openTimePickerDialog.value = false
                    }
                ){
                    Text(text = "CANCEL")
                }
            }
        ){
            TimeInput(
                state = timePickerState,
                modifier = Modifier.padding(horizontal = 26.dp, vertical = 16.dp)

            )

        }
    }






}