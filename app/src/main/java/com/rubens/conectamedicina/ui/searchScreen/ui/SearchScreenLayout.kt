package com.rubens.conectamedicina.ui.searchScreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rubens.conectamedicina.VerticalListItem
import com.rubens.conectamedicina.shimmertutorial.ShimmerDoctorList
import com.rubens.conectamedicina.ui.searchScreen.viewmodel.SearchScreenViewModel

@Composable
fun SearchScreenLayout(
                       viewModel: SearchScreenViewModel,
                       snackbarHostState: SnackbarHostState){



    val doctorsList by remember { viewModel.doctors }
    val showPlaceholderText by remember { viewModel.showPlaceHolderText }
    var searchBarText by remember { viewModel.searchBarText }
    val isLoadingDoctors by remember { viewModel.loadingDoctors }
    val errorGettingDoctors by remember { viewModel.errorGettingDoctors }

    if(errorGettingDoctors != ""){
        LaunchedEffect(Unit){
            viewModel.setIsLoadingDoctors(false)
            snackbarHostState.showSnackbar(message = errorGettingDoctors,
                actionLabel = "Ok")
        }

    }





    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 26.dp)) {

        OutlinedTextField(
            value = searchBarText,
            onValueChange = {
                searchBarText = it
                viewModel.setSearchBarText(it)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF43C2FF),
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color(0xFF43C2FF),
            )

        )

        if(showPlaceholderText){
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)){
                Text(
                    text = "Search For Doctors",
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "Search for doctors by name, location or specialty"
                )
            }
        }


        ShimmerDoctorList(
            isLoading = isLoadingDoctors,
            contentAfterLoading = {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    items(doctorsList){
                            item->
                        VerticalListItem(item = item){

                        }


                    }
                }
            }
        )




    }




}


