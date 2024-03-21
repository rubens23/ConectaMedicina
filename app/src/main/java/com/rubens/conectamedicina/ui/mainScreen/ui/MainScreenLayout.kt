package com.rubens.conectamedicina.ui.mainScreen.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import com.rubens.conectamedicina.CircularImageWithBackgroundUserProfileImage
import com.rubens.conectamedicina.HorizontalListItem
import com.rubens.conectamedicina.LabelText
import com.rubens.conectamedicina.data.doctors.MedicalCategory
import com.rubens.conectamedicina.NotificationWithBadge
import com.rubens.conectamedicina.R
import com.rubens.conectamedicina.SearchIcon
import com.rubens.conectamedicina.UserWelcomingText
import com.rubens.conectamedicina.VerticalListItem
import com.rubens.conectamedicina.shimmertutorial.ShimmerCircularImageWithBackground
import com.rubens.conectamedicina.shimmertutorial.ShimmerDoctorList
import com.rubens.conectamedicina.shimmertutorial.ShimmerHorizontalListItem
import com.rubens.conectamedicina.shimmertutorial.ShimmerLabelText
import com.rubens.conectamedicina.shimmertutorial.ShimmerNotificationWithBadge
import com.rubens.conectamedicina.shimmertutorial.ShimmerSearchIcon
import com.rubens.conectamedicina.shimmertutorial.ShimmerUserWelcomingText
import com.rubens.conectamedicina.ui.mainScreen.viewModel.MainScreenViewModel
import java.util.Locale


@Composable
fun MainScreenLayout(
    viewModel: MainScreenViewModel,
    onDoctorChosen: (String, String, String) -> Unit,
    goToSearchScreen: () ->Unit,
    goToNotificationScreen: ()-> Unit,
    snackbarHostState: SnackbarHostState
) {


    val user by remember { viewModel.user }
    val loadingUser by remember { viewModel.userLoading }
    val doctors by remember { viewModel.doctors }
    val loadingDoctors by remember { viewModel.doctorsLoading }
    val chosenMedicalCategory by remember { viewModel.chosenMedicalCategory }
    val resultSaveNewProfileImage by remember { viewModel.resultSaveNewProfileImage }

    LaunchedEffect(Unit){
        if(resultSaveNewProfileImage != ""){
            snackbarHostState.showSnackbar(message = resultSaveNewProfileImage, actionLabel = "Ok")
        }
    }




    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(top = 40.dp, start = 20.dp)

        ) {
            user?.let {
                ShimmerCircularImageWithBackground(
                    isLoading = loadingUser,
                    contentAfterLoading = {
                        CircularImageWithBackgroundUserProfileImage(
                            userUsername = it.username,
                            userProfilePicture = it.profilePicture,
                            viewModel = viewModel
                        )
                    },
                )
                ShimmerUserWelcomingText(
                    isLoading = loadingUser,
                    contentAfterLoading = {
                        UserWelcomingText(
                            userName = it.name.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            },
                            modifier = Modifier.padding(top = 8.dp, start = 12.dp)
                        )
                    }
                )


                Spacer(modifier = Modifier.weight(1f))

                ShimmerSearchIcon(
                    isLoading = loadingUser,
                    contentAfterLoading = {
                        SearchIcon(
                            modifier = Modifier.padding(top = 5.dp),
                            goToSearchScreen = {
                                goToSearchScreen()

                            }
                        )
                    }
                )
            }

            ShimmerNotificationWithBadge(
                isLoading = loadingUser,
                contentAfterLoading = { NotificationWithBadge(goToNotificationsScreen = {
                    goToNotificationScreen()

                }) }
            )


        }
        Spacer(Modifier.height(20.dp))

        ShimmerLabelText(
            isLoading = loadingUser,
            contentAfterLoading = { LabelText("Categories") }
        )

        LazyRow(
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(listOfCategories) { item ->
                ShimmerHorizontalListItem(
                    isLoading = loadingUser,
                    contentAfterLoading = {
                        HorizontalListItem(item) {
                            viewModel.setMedicalCategory(it.name)

                        }
                    }
                )
            }
        }


        Spacer(Modifier.height(20.dp))

        ShimmerLabelText(
            isLoading = loadingDoctors,
            contentAfterLoading = {
                LabelText(if (chosenMedicalCategory.isEmpty()) "All Doctors" else "$chosenMedicalCategory doctors")
            }
        )

        ShimmerDoctorList(
            modifier = Modifier.padding(horizontal = 16.dp),
            isLoading = loadingDoctors,
            contentAfterLoading = {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    items(doctors) { item ->
                        VerticalListItem(item = item) {
                            if(user != null){
                                Log.d("MainScreenLayout", "$it")
                                onDoctorChosen(it.username, user!!.username, user!!.profilePicture)

                            }

                        }


                    }
                }

            }
        )



    }


}


//later on implement this to be caught from the server
//through a http endpoint
val listOfCategories = listOf(
    MedicalCategory(
        name = "Endocrinology",
        categoryImage = R.drawable.endocrinology
    ),
    MedicalCategory(
        name = "Cardiology",
        categoryImage = R.drawable.cardiology
    ),
    MedicalCategory(
        name = "Neurology",
        categoryImage = R.drawable.neurology
    ),
    MedicalCategory(
        name = "Odontology",
        categoryImage = R.drawable.dentist
    ),
    MedicalCategory(
        name = "Orthopedics",
        categoryImage = R.drawable.orthopedics
    ),
    MedicalCategory(
        name = "Pediatrics",
        categoryImage = R.drawable.pediatrics
    ),
    MedicalCategory(
        name = "Radiology",
        categoryImage = R.drawable.radiology
    ),
    MedicalCategory(
        name = "Oncology",
        categoryImage = R.drawable.oncology

    ),
    MedicalCategory(
        name = "Urology",
        categoryImage = R.drawable.urology

    )
)