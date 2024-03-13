package com.rubens.conectamedicina.ui.reviewAndFeedbackScreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rubens.conectamedicina.data.user.User
import com.rubens.conectamedicina.ui.reviewAndFeedbackScreen.viewModel.ReviewAndFeedbacksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewAndFeedbackLayout(viewModel: ReviewAndFeedbacksViewModel,
                            doctorUsername: String,
                            snackbarHostState: SnackbarHostState,
                            userName: String) {


    val showFeedbackBox = remember { mutableStateOf(false) }
    val feedbackText = remember { mutableStateOf("") }
    val ratingValue = remember { mutableIntStateOf(0) }
    val reviewSaveStatus by remember { viewModel.reviewSaveStatus }

    if(reviewSaveStatus != ""){
        LaunchedEffect(Unit){
            snackbarHostState.showSnackbar(
                message = reviewSaveStatus,
                actionLabel = "Ok"
            )
        }
    }



    Column(modifier = Modifier.padding(horizontal = 16.dp)
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center) {
        Text(
            text = "How would you rate this doctor?",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(20.dp))

        Row(modifier = Modifier.padding(horizontal = 56.dp)) {
            Text(
                text = "Bad",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                fontSize = 22.sp,
                color = Color.Gray
            )
            Text(
                text = "Great",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                fontSize = 22.sp,
                color = Color.Gray
            )
        }

        Spacer(Modifier.height(20.dp))

        StarRatingSystem(){
                rating->
            //clicked on a rating, can show feedback box
            ratingValue.value = rating
            showFeedbackBox.value = true
        }

        Spacer(Modifier.height(40.dp))

        if(showFeedbackBox.value){

            OutlinedTextField(
                value = feedbackText.value,
                onValueChange = { feedbackText.value = it },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF43C2FF),
                    cursorColor = Color(0xFF43C2FF)
                ),
                placeholder = { Text("Leave a message..") },
                modifier = Modifier.fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 16.dp),
                maxLines = 5


            )

            Spacer(Modifier.height(40.dp))

            Button(
                onClick = {
                    viewModel.saveNewReview(feedbackText = feedbackText.value,
                        rating = ratingValue.intValue,
                        doctorUsername = doctorUsername,
                        name = userName)

                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF43C2FF))
            ) {

                Text(
                    text = "Submit",
                    modifier = Modifier.padding(vertical = 20.dp),
                    color = Color.White
                )
            }

        }
    }

}