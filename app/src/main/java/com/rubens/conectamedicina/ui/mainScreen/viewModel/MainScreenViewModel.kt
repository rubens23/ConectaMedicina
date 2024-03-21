package com.rubens.conectamedicina.ui.mainScreen.viewModel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubens.conectamedicina.data.auth.AuthRepository
import com.rubens.conectamedicina.data.auth.di.UserAuthRepositoryQualifier
import com.rubens.conectamedicina.data.doctors.Doctor
import com.rubens.conectamedicina.data.doctors.DoctorDataSource
import com.rubens.conectamedicina.data.notification.ApiService
import com.rubens.conectamedicina.data.storage.FileStorage
import com.rubens.conectamedicina.data.storage.StorageDto
import com.rubens.conectamedicina.data.user.User
import com.rubens.conectamedicina.data.user.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    @UserAuthRepositoryQualifier private val userAuthRepository: AuthRepository,
    private val userDataSource: UserDataSource,
    private val doctorDataSource: DoctorDataSource,
    private val pushNotificationsService: ApiService,
    private val fileStorage: FileStorage,



    ): ViewModel() {

        private val tag = "MainScreenViewModel"



    private var _chosenMedicalCategory = mutableStateOf("")
    val chosenMedicalCategory: State<String> get() = _chosenMedicalCategory

    private var _user = mutableStateOf<User?>(null)
    val user: State<User?> get() = _user

    private var _doctors = mutableStateOf<List<Doctor>>(emptyList())
    val doctors get() = _doctors

    private var _allDoctors = mutableStateOf<List<Doctor>>(emptyList())
    val allDoctors get() = _allDoctors

    private var _userLoading = mutableStateOf(true)
    val userLoading: State<Boolean> get() = _userLoading

    private var _doctorsLoading = mutableStateOf(true)
    val doctorsLoading: State<Boolean> get() = _doctorsLoading

    private val _resultSaveNewProfileImage = mutableStateOf("")
    val resultSaveNewProfileImage = _resultSaveNewProfileImage


    init {
        loadUserInfo()

    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            val userSecret = userAuthRepository.getUserSecret()
            if (userSecret != null) {
                getUserByUserId(userSecret)
            }else{
                _user.value = null
            }
        }
    }

    private fun getUserByUserId(secret: String) {
        viewModelScope.launch {
            val user = userDataSource.getUserByUserId(secret)
            if(user != null){
                _user.value = user
                _userLoading.value = false
                getDoctorsFromDb()
                setPushNotificationsUserId(user.username)

            }
        }

    }

    fun getDoctorsFromDb() {
        viewModelScope.launch {
            val doctors = doctorDataSource.getAllDoctors()
            if (doctors != null) {
                _doctors.value = doctors.doctors
                _allDoctors.value = doctors.doctors
                _doctorsLoading.value = false

            }


        }
    }



    fun setMedicalCategory(medicalCategory: String) {
        _chosenMedicalCategory.value = medicalCategory
        chooseDoctorsFromThisCategory(medicalCategory)

    }

    private fun chooseDoctorsFromThisCategory(medicalCategory: String) {

        val lowercaseSearchText = medicalCategory.toLowerCase(Locale.current)

        _doctors.value = allDoctors.value.filter { doctor ->
            doctor.name.toLowerCase(Locale.current).contains(lowercaseSearchText) ||
                    doctor.specialty.toLowerCase(Locale.current).contains(lowercaseSearchText)
        }

    }

    fun setPushNotificationsUserId(username: String) {
        viewModelScope.launch(Dispatchers.IO) {

            pushNotificationsService.changeUserExternalId(username)

        }


    }

    fun saveProfileImageToStorage(uri: Uri?, userUsername: String) {
        if(uri != null){
            viewModelScope.launch {
                fileStorage.uploadImageToStorage(
                    uri,
                    userUsername
                ){ imgLink->
                    Log.d(tag, "imgLink = $imgLink")
                    viewModelScope.launch {
                        userDataSource.updateUserProfilePicture(
                            StorageDto(
                            imgLink = imgLink,
                            userUsername = userUsername
                        )
                        ) { updated ->
                            if (updated) {
                                Log.d("solvingProfPicture", "vou alterar o estado do user")
                                _user.value.let { currentUser ->
                                    _user.value = currentUser!!.copy(profilePicture = imgLink)
                                }
                            }else{
                                _resultSaveNewProfileImage.value = "There was an error saving new profile image"

                            }
                        }
                    }

                }
            }
        }

    }




}