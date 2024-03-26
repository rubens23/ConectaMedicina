package com.rubens.conectamedicina.ui

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.rubens.conectamedicina.ui.signinScreen.data.auth.AuthState
import com.rubens.conectamedicina.data.auth.AuthRepository
import com.rubens.conectamedicina.data.auth.AuthResult
import com.rubens.conectamedicina.data.auth.di.UserAuthRepositoryQualifier
import com.rubens.conectamedicina.data.doctors.AllDoctors
import com.rubens.conectamedicina.data.doctors.Doctor
import com.rubens.conectamedicina.data.doctors.DoctorDataSource
import com.rubens.conectamedicina.data.user.User
import com.rubens.conectamedicina.data.user.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @UserAuthRepositoryQualifier private val userAuthRepository: AuthRepository,
    private val doctorDataSource: DoctorDataSource,
    private val userDataSource: UserDataSource,
    private val sharedPreferences: SharedPreferences,
): ViewModel(){

    var doctorsList: List<Doctor> = emptyList()
    private val TAG = "MainViewModel"

   var selectedRadioItem: String = ""
       private set
    var state by mutableStateOf(AuthState())

    private val resultChannel = Channel<AuthResult<Unit>>()
    private val doctorsChannel = Channel<AllDoctors>()
    private val userChannel = Channel<User>()
    private val userSecretChannel = Channel<String>()

    val userSecretResults = userSecretChannel.receiveAsFlow()
    val userResult = userChannel.receiveAsFlow()


    private val highestRankingDoctors = Channel<List<Doctor>>()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()



    init {

        authenticate()
        getDoctorsFromDb()


        //this is fine for saving the token when the viewModel is created
        //todo check if it is also fine for when the token changes

        saveNotificationToken()

    }

    private fun saveNotificationToken() {
        viewModelScope.launch {
            val username = sharedPreferences.getString("username", null)?:return@launch
            val fcmToken = Firebase.messaging.token.await()

            userDataSource.updateUserNotificationToken(
                fcmToken = fcmToken,
                username = username
            )
        }
    }





    private fun authenticate(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = userAuthRepository.authenticate()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    fun setSelectedRadioItem(item: String) {
        selectedRadioItem = item

    }





    fun getDoctorsFromDb() {
        viewModelScope.launch {
            val doctors = doctorDataSource.getAllDoctors()
            if (doctors != null) {
                doctorsChannel.send(doctors)
                doctorsList = doctors.doctors
                getTopRatedDoctors()
            }


        }
    }

    fun getTopRatedDoctors() {
        viewModelScope.launch {
            val doctors = doctorDataSource.getAllDoctors()
            Log.d(TAG, "$doctors")

            if (doctors != null){
                val topRatedDoctors = doctors.doctors.sortedByDescending { it.rating }.take(10)
                Log.d(TAG, "topRatedDoctors $topRatedDoctors")

                highestRankingDoctors.send(topRatedDoctors)

            }





        }
    }





    fun getUserSecret(){
        viewModelScope.launch {
            val userSecret = userAuthRepository.getUserSecret()
            _loading.value = false
            if (userSecret != null) {
                userSecretChannel.send(userSecret)
            }else{
                userSecretChannel.send("doesn't have a secret")
            }

        }
    }

    fun getUserByUserId(secret: String) {
        viewModelScope.launch {
            val user = userDataSource.getUserByUserId(secret)
            if(user != null){
                userChannel.send(user)
            }
        }

    }














}

