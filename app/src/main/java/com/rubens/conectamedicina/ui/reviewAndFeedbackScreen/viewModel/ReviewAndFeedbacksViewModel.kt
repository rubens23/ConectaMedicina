package com.rubens.conectamedicina.ui.reviewAndFeedbackScreen.viewModel

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubens.conectamedicina.data.reviews.Review
import com.rubens.conectamedicina.data.reviews.ReviewsAndFeedbacksDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewAndFeedbacksViewModel @Inject constructor(
    private val reviewsAndFeedbackDataSource: ReviewsAndFeedbacksDataSource,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private val _reviewSaveStatus = mutableStateOf("")
    val reviewSaveStatus get() = _reviewSaveStatus


    fun saveNewReview(feedbackText: String,
                      rating: Int,
                      doctorUsername: String,
                      name: String){
        viewModelScope.launch {
            val username = sharedPreferences.getString("username", null)?: return@launch

            val newAppointmentWasSaved = reviewsAndFeedbackDataSource.saveNewReview( Review(userUsername = username,
                doctorUsername = doctorUsername,
                rating = rating,
                feedbackText = feedbackText,
                timestamp = System.currentTimeMillis().toString(),
                name = name))

            if(newAppointmentWasSaved){
                _reviewSaveStatus.value = "The review was saved successfully!"

            }else{
                _reviewSaveStatus.value = "There was an error saving the review!"

            }

        }
    }
}