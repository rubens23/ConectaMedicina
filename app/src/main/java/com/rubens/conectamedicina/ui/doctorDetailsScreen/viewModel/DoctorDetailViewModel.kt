package com.rubens.conectamedicina.ui.doctorDetailsScreen.viewModel

import android.util.Log
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubens.conectamedicina.data.appointments.Appointment
import com.rubens.conectamedicina.data.appointments.AppoitmentsDataSource
import com.rubens.conectamedicina.data.doctors.Doctor
import com.rubens.conectamedicina.data.doctors.DoctorDataSource
import com.rubens.conectamedicina.data.reviews.Review
import com.rubens.conectamedicina.data.reviews.ReviewsAndFeedbacksDataSource
import com.rubens.conectamedicina.data.user.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorDetailViewModel @Inject constructor(
    private val doctorDataSource: DoctorDataSource,
    private val reviewsAndFeedbacksDataSource: ReviewsAndFeedbacksDataSource,
    private val appoitmentsDataSource: AppoitmentsDataSource,
    private val userDataSource: UserDataSource,



    ) : ViewModel() {


    private val _doctorChannel = mutableStateOf<Doctor?>(null)
    val doctorResult get() = _doctorChannel

    private val _errorGettingDoctor = mutableStateOf("")
    val errorGettingDoctor get() = _errorGettingDoctor

    private val reviewsChannel = Channel<List<Review>>()
    val reviewsResult = reviewsChannel.receiveAsFlow()

    private val appointmentsChannel = Channel<List<Appointment>>()
    val appointmentsResult get() = appointmentsChannel.receiveAsFlow()

    private val userProfilePhotoForReview = Channel<String>()
    val userProfilePhotoForReviewResults = userProfilePhotoForReview.receiveAsFlow()






    fun getDoctorByUsername(username: String) {
        viewModelScope.launch {
            val doctor = doctorDataSource.getDoctorByUsername(username)
            if (doctor != null) {
                _doctorChannel.value = doctor
            }else{
                _errorGettingDoctor.value = "There was an error getting this doctor's info"
            }
        }

    }

    fun getAllReviewsByDoctorUsername(doctorUsername: String) {
        viewModelScope.launch {
            val reviews = reviewsAndFeedbacksDataSource.getAllReviewsByDoctorUsername(doctorUsername)
            if(reviews != null){
                reviewsChannel.send(reviews)
            }

        }


    }

    fun getDoctorAppointmentsByDoctorUsername(doctorUsername: String){
        viewModelScope.launch {
            val appointments = appoitmentsDataSource.getDoctorAppointmentsByDoctorUsername(doctorUsername)
            if (appointments != null){
                appointmentsChannel.send(appointments)
            }
        }

    }

    fun getUserProfilePictureByUsername(userUsername: String){
        viewModelScope.launch {
            val photoUrl = userDataSource.getUserProfilePictureByUsername(userUsername)
            userProfilePhotoForReview.send(photoUrl)
        }

    }

}