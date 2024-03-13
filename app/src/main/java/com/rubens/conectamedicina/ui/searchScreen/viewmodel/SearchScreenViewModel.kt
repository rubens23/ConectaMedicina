package com.rubens.conectamedicina.ui.searchScreen.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubens.conectamedicina.data.doctors.Doctor
import com.rubens.conectamedicina.data.doctors.DoctorDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val doctorDataSource: DoctorDataSource,
    ): ViewModel() {

    private var _doctors = mutableStateOf<List<Doctor>>(emptyList())
    val doctors get() = _doctors

    private var _searchBarText = mutableStateOf("")
    val searchBarText get() = _searchBarText

    private var _loadingDoctors = mutableStateOf(false)
    val loadingDoctors get() = _loadingDoctors

    private var _showPlaceholderText = mutableStateOf(true)
    val showPlaceHolderText get() = _showPlaceholderText

    private var _allDoctors = mutableStateOf<List<Doctor>>(emptyList())
    private val allDoctors get() = _allDoctors

    private var _errorGettingDoctors = mutableStateOf("")
    val errorGettingDoctors get() = _errorGettingDoctors

    init {
        getDoctorsFromDb()
    }


    private fun getDoctorsFromDb() {
        viewModelScope.launch {
            val doctors = doctorDataSource.getAllDoctors()
            if (doctors != null) {
                _allDoctors.value = doctors.doctors

            }else{
                _errorGettingDoctors.value = "There was an error getting all doctors"
            }


        }
    }

    private fun sortDoctorsListBySearchText(){

        if(searchBarText.value.isNotEmpty()){
            _loadingDoctors.value = true
            _showPlaceholderText.value = false
            val lowercaseSearchText = searchBarText.value.toLowerCase(Locale.current)

            _doctors.value =  allDoctors.value.filter {
                    doctor->
                doctor.name.toLowerCase(Locale.current).contains(lowercaseSearchText) ||
                        doctor.specialty.toLowerCase(Locale.current).contains(lowercaseSearchText)
            }
                _loadingDoctors.value = false



        }




    }

    fun setSearchBarText(newText: String) {
        _searchBarText.value = newText
        if (errorGettingDoctors.value != "There was an error getting all doctors"){
            sortDoctorsListBySearchText()

        }else{
            //try to reassign state to call the snackbar again
            _errorGettingDoctors.value = "There was an error getting all doctors"
        }

    }

    fun setIsLoadingDoctors(isLoading: Boolean) {
        _loadingDoctors.value = isLoading
    }
}