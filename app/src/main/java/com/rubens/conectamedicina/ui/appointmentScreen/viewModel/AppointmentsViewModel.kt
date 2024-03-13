package com.rubens.conectamedicina.ui.appointmentScreen.viewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubens.conectamedicina.data.appointments.Appointment
import com.rubens.conectamedicina.data.appointments.AppoitmentsDataSource
import com.rubens.conectamedicina.ui.appointmentScreen.NewAppointmentEvent
import com.rubens.conectamedicina.ui.appointmentScreen.NewAppointmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class AppointmentsViewModel @Inject constructor(
    private val appointmentsDataSource: AppoitmentsDataSource,
    private val sharedPreferences: SharedPreferences,
    private val appoitmentsDataSource: AppoitmentsDataSource,

    ): ViewModel() {

    //todo terminar de passar os valores la da view para ca, para a vm controlar o estado
    //todo salvar corretamente a appointment e mostrar a snackbar quando o server estiver caido


    private val _appointmentsAreLoading = mutableStateOf(true)
    val appointmentsAreLoading get() = _appointmentsAreLoading


    private var _name = mutableStateOf("")
    val name get() = _name



    private var _age= mutableStateOf("")
    val age get() = _age
    private var _date = mutableStateOf("")
    val date get()= _date
    private var _visitTime =  mutableStateOf("")
    val visitTime get() =  _visitTime
    private var _condition = mutableStateOf("")
    val condition get()= _condition
    private var _description = mutableStateOf("")
    val description get() = _description





   var chosenHour = mutableStateOf("")
   var chosenDay = mutableStateOf("")
   var chosenMonth = mutableStateOf("")
   var dateFormatted = mutableStateOf("")
    var newAppointmentState by mutableStateOf(NewAppointmentState())





    val TAG = "AppointmentsViewModel"

    private val appointmentsChannel = Channel<List<Appointment>>()
    val appointmentsResult = appointmentsChannel.receiveAsFlow()

    private val _errorGettingAppointments = mutableStateOf("")
    val errorGettingAppointments get() = _errorGettingAppointments

    private val _appointmentSaveStatus = mutableStateOf("")
    val appointmentSaveStatus get() = _appointmentSaveStatus

    init {
        getUserAppointments()
    }

    fun onAppointmentEvent(event: NewAppointmentEvent){
        when(event){
            is NewAppointmentEvent.AppointmentNameChanged ->{newAppointmentState = newAppointmentState.copy(name = event.value)}
            is NewAppointmentEvent.AppointmentAgeChanged ->{newAppointmentState = newAppointmentState.copy(age = event.value)}
            is NewAppointmentEvent.AppointmentDateChanged ->{newAppointmentState = newAppointmentState.copy(date = event.value)}
            is NewAppointmentEvent.AppointmentVisitTimeChanged ->{newAppointmentState = newAppointmentState.copy(visitTime = event.value)}
            is NewAppointmentEvent.AppointmentConditionChanged ->{newAppointmentState = newAppointmentState.copy(condition = event.value)}
            is NewAppointmentEvent.AppointmentDescriptionChanged ->{newAppointmentState = newAppointmentState.copy(description = event.value)}
            is NewAppointmentEvent.AppointmentChosenHourChanged ->{newAppointmentState = newAppointmentState.copy(chosenHour = event.value)}
            is NewAppointmentEvent.AppointmentChosenDayChanged ->{newAppointmentState = newAppointmentState.copy(chosenDay = event.value)}
            is NewAppointmentEvent.AppointmentChosenMonthChanged ->{newAppointmentState = newAppointmentState.copy(chosenMonth = event.value)}
            is NewAppointmentEvent.AppointmentDoctorNameChanged ->{newAppointmentState = newAppointmentState.copy(doctorName = event.value)}
            is NewAppointmentEvent.AppointmentClientNameChanged ->{newAppointmentState = newAppointmentState.copy(clientName = event.value)}
            is NewAppointmentEvent.AppointmentServiceChanged ->{newAppointmentState = newAppointmentState.copy(service = event.value)}
            is NewAppointmentEvent.AppointmentClientIdChanged ->{newAppointmentState = newAppointmentState.copy(clientId = event.value)}
            is NewAppointmentEvent.AppointmentDoctorIdChanged ->{newAppointmentState = newAppointmentState.copy(doctorId = event.value)}
            is NewAppointmentEvent.AppointmentAppointmentDayFormattedChanged ->{newAppointmentState = newAppointmentState.copy(appointmentDayFormatted = event.value)}
            is NewAppointmentEvent.AppointmentClientPhotoChanged ->{newAppointmentState = newAppointmentState.copy(userPhotoUrl = event.value)}
        }
    }


    fun getUserAppointments() {
        viewModelScope.launch {
            val username = sharedPreferences.getString("username", null)?: return@launch
            val appointments = appointmentsDataSource.pegarTodosAppointments(username)
            if (appointments != null){
                _appointmentsAreLoading.value = false
                appointmentsChannel.send(appointments)
            }else{
                _errorGettingAppointments.value = "There was an error getting the appointments"
            }
        }
    }


    fun getDayOfTheWeekByDate(date: String): String{
        val format = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        return try {
            val dt = format.parse(date)
            val outputFormat = SimpleDateFormat("EEE", Locale.getDefault())

            return dt?.let { outputFormat.format(it) }?:""
        }catch (e: Exception){
            Log.e(TAG, "error on getDayOfTheWeekByDate ${e.message}")
            ""
        }
    }


    fun getDay(selectedDateMillis: Long?): String {
        if (selectedDateMillis == null){
            return ""

        }
        val sdf = SimpleDateFormat("dd", java.util.Locale.getDefault())
        val date = Date(selectedDateMillis)
        return sdf.format(date)

    }

    fun getMonth(selectedDateMillis: Long?): String {
        if (selectedDateMillis == null){
            return ""

        }

        val sdf = SimpleDateFormat("MMMM", java.util.Locale.getDefault())
        val date = Date(selectedDateMillis)
        return sdf.format(date)




    }

    fun formatMillisecondsDateToDateFormat(selectedMillis: Long?): String {
        if (selectedMillis == null) {
            return ""
        }
        val dateFormat = DateFormat.getDateInstance()
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        return dateFormat.format(Date(selectedMillis))




    }

    fun saveNewAppointment(){
        viewModelScope.launch {
            val wasSaved = appoitmentsDataSource.saveNewAppointment(
                Appointment(
                    day = newAppointmentState.chosenDay,
                    month = newAppointmentState.chosenMonth,
                    doctor = newAppointmentState.doctorName,
                    hour = newAppointmentState.chosenHour,
                    service = newAppointmentState.service,
                    clientName = newAppointmentState.clientName,
                    clientId = newAppointmentState.clientId,
                    doctorId = newAppointmentState.doctorId,
                    appointmentDayFormatted = newAppointmentState.appointmentDayFormatted,
                    clientPhotoUrl = newAppointmentState.userPhotoUrl
                )
            )
            if(!wasSaved){
                _appointmentSaveStatus.value = "Your appointment was saved successfully!"
            }else{
                _appointmentSaveStatus.value = "There was an error saving your appointment! Try again later"
            }
        }
    }

    fun setAppointmentsAreLoading(isLoading: Boolean) {
        _appointmentsAreLoading.value = isLoading

    }
}