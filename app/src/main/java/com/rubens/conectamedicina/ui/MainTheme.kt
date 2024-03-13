package com.rubens.conectamedicina.ui

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rubens.conectamedicina.ui.appointmentScreen.ui.AppointmentsListLayout
import com.rubens.conectamedicina.ui.navigation.BottomNavItem
import com.rubens.conectamedicina.ui.chatScreen.ui.ClientDoctorChatScreenLayout
import com.rubens.conectamedicina.ui.doctorDetailsScreen.ui.DoctorsDetailScreenLayout
import com.rubens.conectamedicina.ui.mainScreen.ui.MainScreenLayout
import com.rubens.conectamedicina.ui.appointmentScreen.ui.NewAppointmentLayout
import com.rubens.conectamedicina.ui.appointmentScreen.viewModel.AppointmentsViewModel
import com.rubens.conectamedicina.ui.chatScreen.viewModel.ChatViewModel
import com.rubens.conectamedicina.ui.doctorDetailsScreen.viewModel.DoctorDetailViewModel
import com.rubens.conectamedicina.ui.notificatioScreen.ui.NotificationScreenLayout
import com.rubens.conectamedicina.ui.searchScreen.ui.SearchScreenLayout
import com.rubens.conectamedicina.ui.signinScreen.ui.SignInScreen
import com.rubens.conectamedicina.ui.navigation.BottomNavigation
import com.rubens.conectamedicina.ui.navigation.EmptyBottomNavigation
import com.rubens.conectamedicina.ui.mainScreen.viewModel.MainScreenViewModel
import com.rubens.conectamedicina.ui.notificatioScreen.viewModel.NotificationViewModel
import com.rubens.conectamedicina.ui.reviewAndFeedbackScreen.ui.ReviewAndFeedbackLayout
import com.rubens.conectamedicina.ui.reviewAndFeedbackScreen.viewModel.ReviewAndFeedbacksViewModel
import com.rubens.conectamedicina.ui.searchScreen.viewmodel.SearchScreenViewModel
import com.rubens.conectamedicina.ui.signinScreen.ui.SignUpScreen
import com.rubens.conectamedicina.ui.signinScreen.viewModels.AuthenticationViewModel
import kotlinx.coroutines.launch


//todo hide bottom navigation when scrolling

@Composable
fun MainTheme(viewModel: MainViewModel = hiltViewModel(),
              modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var showHomeScreen by remember { mutableStateOf(false) }
    var showSignUpScreen by remember { mutableStateOf(false) }
    var userUsername by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var userSecret by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember{SnackbarHostState()}



    viewModel.getUserSecret()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            viewModel.userSecretResults.collect {
                if (it != "doesn't have a secret"){
                    showHomeScreen = true
                    userSecret = it
                    viewModel.getUserByUserId(it)

                } else{
                    showSignUpScreen =
                        true
                }
            }
        }
    }

    LaunchedEffect(Unit){
        viewModel.userResult.collect{

            userUsername = it.username
            @Suppress("SENSELESS_COMPARISON")
            if(it.name != null){
                name = it.name

            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {SnackbarHost(hostState = snackbarHostState)},
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = { if(userUsername != "") BottomNavigation(navController,
            goToNotificationsScreen = {
                navController.navigate(BottomNavItem.Notification.screenRoute){
                    popUpTo(BottomNavItem.Home.screenRoute){
                        saveState = true
                    }
                    launchSingleTop = true
                }
            },
            goToSearchScreen = {
                navController.navigate(BottomNavItem.SearchScreen.screenRoute){
                    popUpTo(BottomNavItem.Home.screenRoute){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            goToAppointmentsScreen = {
                navController.navigate(BottomNavItem.Appointments.screenRoute){

                    popUpTo(BottomNavItem.Home.screenRoute){
                        saveState = true
                    }
                    launchSingleTop = true
                }
            },
            goToHomeScreen = {
                navController.navigate(BottomNavItem.Home.screenRoute){
                    popUpTo(BottomNavItem.Home.screenRoute){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }) else EmptyBottomNavigation() }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination =  if(showSignUpScreen) BottomNavItem.SignUp.screenRoute else if(userSecret != "" && userSecret != "doesn't have a secret")BottomNavItem.Home.screenRoute else BottomNavItem.SignUp.screenRoute,
            modifier = Modifier.padding(innerPadding)
        ) {



            composable(route = "splash") {
                SplashScreen()
            }

//            composable(route = "${BottomNavItem.DoctorDetails.screenRoute}/{doctorEmail}",
//                arguments = listOf(
//                    navArgument("doctorEmail"){type = NavType.StringType}
//                )
//            ){backStackEntry->
//                val doctorUsername = backStackEntry.arguments!!.getString("doctorEmail")!!
//
//
//
//
////                DoctorsDetailScreenLayout(
////                    doctorUsername = "",
////                    userUsername = "",
////                    goToChatScreen = { doctorUserName, userUsername->
////                        navController.navigate("${BottomNavItem.ClientDoctorChat.screenRoute}/${doctorUserName}/${userUsername}")
////
////                    },
////                    goToReviewScreen = { doctorUserName->
////                        navController.navigate("${BottomNavItem.ReviewsAndFeedbacks.screenRoute}/${doctorUserName}")
////
////                    },
////                    goToNewAppointmentScreen = { doctorName, doctorSpecialty, clientName, clientId, doctorUserName, clientPhotoUrl ->
////                        navController.navigate("${BottomNavItem.NewAppointment.screenRoute}/${doctorName}/${doctorSpecialty}/${clientName}/${clientId}/${doctorUserName}/${clientPhotoUrl}")
////
////                    },
////                    goBackToHomeScreen = {
////                        navController.popBackStack(route = BottomNavItem.Home.screenRoute,
////                            inclusive = false,
////                            saveState = false)
////                    },
////                    viewModel = hiltViewModel<DoctorDetailViewModel>(),
////                    snackbarHostState = snackbarHostState,
////                    userPhotoUrl = ""
////                )
//            }

            composable(route = BottomNavItem.Home.screenRoute) {

                val mainViewModel = hiltViewModel<MainScreenViewModel>()

                MainScreenLayout(
                    viewModel = mainViewModel,
                    onDoctorChosen = {doctorUsername: String, userUsername, userPhoto ->

                        navController.navigate("${BottomNavItem.DoctorDetails.screenRoute}/$doctorUsername/$userUsername/${Uri.encode(userPhoto)}"){

                        restoreState = true
                        }

                    },
                    goToSearchScreen = {
                        navController.navigate(BottomNavItem.SearchScreen.screenRoute){
                            popUpTo(BottomNavItem.Home.screenRoute){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    goToNotificationScreen = {
                        navController.navigate(BottomNavItem.Notification.screenRoute){
                            popUpTo(BottomNavItem.Home.screenRoute){
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    })
            }
            composable(route = BottomNavItem.SearchScreen.screenRoute
            ) {

                val searchViewModel = hiltViewModel<SearchScreenViewModel>()

                SearchScreenLayout(
                    viewModel = searchViewModel,
                    snackbarHostState = snackbarHostState)
            }
            composable(route = BottomNavItem.Notification.screenRoute) { NotificationScreenLayout(
                viewModel = hiltViewModel<NotificationViewModel>(),
                snackbarHostState = snackbarHostState
            ) }
            composable(route = "${BottomNavItem.ReviewsAndFeedbacks.screenRoute}/{doctorUsername}",
            arguments = listOf(
                navArgument("doctorUsername"){type = NavType.StringType}
            )
            ) {
                backStackEntry->
                Log.d("MainTheme", "valor do userName $userUsername")
                val doctorUsername = backStackEntry.arguments!!.getString("doctorUsername")!!
                ReviewAndFeedbackLayout(doctorUsername = doctorUsername,
                    viewModel = hiltViewModel<ReviewAndFeedbacksViewModel>(),
                    snackbarHostState = snackbarHostState,
                    userName = name
                )
            }
            composable(route = BottomNavItem.Appointments.screenRoute) { AppointmentsListLayout(
                viewModel = hiltViewModel<AppointmentsViewModel>(),
                snackbarHostState = snackbarHostState
            ) }
            composable(route = "${BottomNavItem.NewAppointment.screenRoute}/{doctorName}/{doctorService}/{clientName}/{clientId}/{doctorId}/{userPhotoUrl}",
                arguments =
                listOf(
                    navArgument("doctorName") { type = NavType.StringType },
                    navArgument("doctorService") { type = NavType.StringType },
                    navArgument("clientName") { type = NavType.StringType },
                    navArgument("clientId") { type = NavType.StringType },
                    navArgument("doctorId") { type = NavType.StringType },
                    navArgument("userPhotoUrl"){type = NavType.StringType}

                )
            ) { backStackEntry ->
                val doctorName = backStackEntry.arguments!!.getString("doctorName")!!
                val doctorService = backStackEntry.arguments!!.getString("doctorService")!!
                val clientName = backStackEntry.arguments!!.getString("clientName")!!
                val clientId = backStackEntry.arguments!!.getString("clientId")!!
                val doctorId = backStackEntry.arguments!!.getString("doctorId")!!
                val userPhoto = backStackEntry.arguments!!.getString("userPhotoUrl")!!

                NewAppointmentLayout(
                    doctorName = doctorName,
                    doctorService = doctorService,
                    clientName = clientName,
                    clientId = clientId,
                    doctorId = doctorId,
                    viewModel = hiltViewModel<AppointmentsViewModel>(),
                    snackbarHostState = snackbarHostState,
                    clientPhotoUrl = userPhoto

                )
            }
            composable(route = BottomNavItem.SignUp.screenRoute) { SignUpScreen(
                viewModel = hiltViewModel<AuthenticationViewModel>(),
                snackbarHostState = snackbarHostState,
                goToLoginScreen = {

                    navController.popBackStack()
                    navController.navigate(BottomNavItem.SignIn.screenRoute){
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) }
            composable(route = BottomNavItem.SignIn.screenRoute) { SignInScreen(changeDestinationAfterLogin = {
                navController.popBackStack()
                navController.navigate(BottomNavItem.Home.screenRoute){
                    launchSingleTop = true
                    restoreState = true
                }

            },
                goToSignUpScreen = {
                    navController.popBackStack()
                    navController.navigate(BottomNavItem.SignUp.screenRoute){
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                viewModel = hiltViewModel<AuthenticationViewModel>(),
                snackbarHostState = snackbarHostState
            ) }
            composable(route = "${BottomNavItem.DoctorDetails.screenRoute}/{doctorUsername}/{userUsername}/{userPhotoUrl}",
                arguments = listOf(
                    navArgument("doctorUsername") {
                        type = NavType.StringType
                    },
                    navArgument("userUsername") {
                        type = NavType.StringType
                    },
                    navArgument("userPhotoUrl"){
                        type = NavType.StringType
                    }
                )) { backStackEntry ->
                val doctorUsername = backStackEntry.arguments?.getString("doctorUsername")
                val userUserName = backStackEntry.arguments?.getString("userUsername")
                val userPhoto = Uri.decode(backStackEntry.arguments?.getString("userPhotoUrl"))



                if (!doctorUsername.isNullOrEmpty() && !userUserName.isNullOrEmpty()) {
                    DoctorsDetailScreenLayout(
                        doctorUsername = doctorUsername,
                        userUsername = userUserName,
                        goToChatScreen = { doctorUserName, userUsername->
                            navController.navigate("${BottomNavItem.ClientDoctorChat.screenRoute}/${doctorUserName}/${userUsername}")

                        },
                        goToReviewScreen = { doctorUserName->
                            navController.navigate("${BottomNavItem.ReviewsAndFeedbacks.screenRoute}/${doctorUserName}")

                        },
                        goToNewAppointmentScreen = { doctorName, doctorSpecialty, clientName, clientId, doctorUserName, clientPhotoUrl ->
                            navController.navigate("${BottomNavItem.NewAppointment.screenRoute}/${doctorName}/${doctorSpecialty}/${clientName}/${clientId}/${doctorUserName}/${clientPhotoUrl}")

                        },
                        goBackToHomeScreen = {
                            navController.popBackStack(route = BottomNavItem.Home.screenRoute,
                                inclusive = false,
                                saveState = false)
                        },
                        viewModel = hiltViewModel<DoctorDetailViewModel>(),
                        snackbarHostState = snackbarHostState,
                        userPhotoUrl = userPhoto?:""
                    )

                    //https://developer.android.com/jetpack/compose/layouts/insets

                }
            }

            composable(
                "${BottomNavItem.ClientDoctorChat.screenRoute}/{doctorUsername}/{userUsername}",
                arguments = listOf(navArgument("doctorUsername") {
                    type = NavType.StringType
                },
                    navArgument("userUsername") {
                        type = NavType.StringType
                    })
            ) { backStackEntry ->
                val doctorUsername = backStackEntry.arguments?.getString("doctorUsername")
                val userUserName = backStackEntry.arguments?.getString("userUsername")
                if (!doctorUsername.isNullOrEmpty() && !userUserName.isNullOrEmpty()) {
                    ClientDoctorChatScreenLayout(
                        doctorUsername = doctorUsername,
                        userUsername = userUserName,
                        viewModel = hiltViewModel<ChatViewModel>(),
                        snackbarHostState = snackbarHostState
                    )

                }
            }


        }

    }
}