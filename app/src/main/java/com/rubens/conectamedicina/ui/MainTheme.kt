package com.rubens.conectamedicina.ui

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    var isChatScreenOpen by remember { mutableStateOf(false) }
    var isDoctorDetailScreenOpen by remember { mutableStateOf(false) }
    var isDoctorFeedbackScreenOpen by remember { mutableStateOf(false) }
    var isNewAppointmentScreenOpen by remember { mutableStateOf(false) }
    var isSignUpScreenOpen by remember { mutableStateOf(true) }
    var isSignInScreenOpen by remember { mutableStateOf(false) }




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

    val navigationBarHeight = WindowInsets.systemBars.asPaddingValues().calculateTopPadding() + WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()

    Log.d("bottomNavOnManualLogin", "o valor de userUsername é $userUsername")



    Scaffold(
        modifier = modifier,
        snackbarHost = {SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(bottom = navigationBarHeight))},
        bottomBar = { if(
             !isChatScreenOpen
            && !isDoctorDetailScreenOpen
            && !isDoctorFeedbackScreenOpen
            && !isNewAppointmentScreenOpen
            && !isSignInScreenOpen
             && !isSignUpScreenOpen


        ) BottomNavigation(navController,
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
            }) else EmptyBottomNavigation() },
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination =  if(showSignUpScreen) BottomNavItem.SignUp.screenRoute else if(userSecret != "" && userSecret != "doesn't have a secret")BottomNavItem.Home.screenRoute else BottomNavItem.SignUp.screenRoute,
            modifier = Modifier.padding(innerPadding)
        ) {





            composable(route = BottomNavItem.Home.screenRoute) {

                val mainViewModel = hiltViewModel<MainScreenViewModel>()

                isChatScreenOpen = false
                isDoctorDetailScreenOpen = false
                isDoctorFeedbackScreenOpen = false
                isNewAppointmentScreenOpen = false
                isSignInScreenOpen = false
                isSignUpScreenOpen = false



                MainScreenLayout(
                    viewModel = mainViewModel,
                    onDoctorChosen = {doctorUsername: String, userUsername, userPhoto ->

                        val encodedUserPhoto = if(userPhoto != "")Uri.encode(userPhoto) else "userHasNoPhoto"

                        Log.d("solvingSpace8", "userPhoto here in home route declaration = $encodedUserPhoto")


                        navController.navigate("${BottomNavItem.DoctorDetails.screenRoute}/$doctorUsername/$userUsername/$encodedUserPhoto"){

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
                    },
                    snackbarHostState = snackbarHostState)
            }
            composable(route = BottomNavItem.SearchScreen.screenRoute
            ) {

                val searchViewModel = hiltViewModel<SearchScreenViewModel>()

                isChatScreenOpen = false
                isDoctorDetailScreenOpen = false
                isDoctorFeedbackScreenOpen = false
                isNewAppointmentScreenOpen = false
                isSignInScreenOpen = false
                isSignUpScreenOpen = false





                SearchScreenLayout(
                    viewModel = searchViewModel,
                    snackbarHostState = snackbarHostState)
            }
            composable(route = BottomNavItem.Notification.screenRoute) {
                isChatScreenOpen = false
                isDoctorDetailScreenOpen = false
                isDoctorFeedbackScreenOpen = false
                isNewAppointmentScreenOpen = false
                isSignInScreenOpen = false
                isSignUpScreenOpen = false




                NotificationScreenLayout(
                viewModel = hiltViewModel<NotificationViewModel>(),
                snackbarHostState = snackbarHostState
            ) }
            composable(route = "${BottomNavItem.ReviewsAndFeedbacks.screenRoute}/{doctorUsername}/{userPhoto}",
            arguments = listOf(
                navArgument("doctorUsername"){type = NavType.StringType},
                navArgument("userPhoto"){type = NavType.StringType}
            )
            ) {
                backStackEntry->
                Log.d("MainTheme", "valor do userName $userUsername")
                val doctorUsername = backStackEntry.arguments!!.getString("doctorUsername")!!
                val userPhotoUrl = backStackEntry.arguments!!.getString("userPhoto")!!

                val encodedUserPhoto = if(userPhotoUrl != "")Uri.encode(userPhotoUrl) else "userHasNoPhoto"


                isChatScreenOpen = false
                isDoctorDetailScreenOpen = false
                isDoctorFeedbackScreenOpen = true
                isNewAppointmentScreenOpen = false
                isSignInScreenOpen = false
                isSignUpScreenOpen = false




                ReviewAndFeedbackLayout(doctorUsername = doctorUsername,
                    viewModel = hiltViewModel<ReviewAndFeedbacksViewModel>(),
                    snackbarHostState = snackbarHostState,
                    userName = name,
                    goBackToDoctorDetails = {
                        Log.d("solvingSpace8", "userPhoto here in review and feedbacks route declaration = $encodedUserPhoto")

                        navController.navigate(route = "${BottomNavItem.DoctorDetails.screenRoute}/$doctorUsername/$userUsername/$encodedUserPhoto")

                    },
                    userPhoto = encodedUserPhoto
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

                isChatScreenOpen = false
                isDoctorDetailScreenOpen = false
                isDoctorFeedbackScreenOpen = false
                isNewAppointmentScreenOpen = true
                isSignInScreenOpen = false
                isSignUpScreenOpen = false




                NewAppointmentLayout(
                    doctorName = doctorName,
                    doctorService = doctorService,
                    clientName = clientName,
                    clientId = clientId,
                    doctorId = doctorId,
                    viewModel = hiltViewModel<AppointmentsViewModel>(),
                    snackbarHostState = snackbarHostState,
                    clientPhotoUrl = userPhoto?:"",
                    goBackToDoctorDetails = {

                        val encodedUserPhoto = if(userPhoto != "")Uri.encode(userPhoto) else "userHasNoPhoto"

                        Log.d("solvingSpace8", "userPhoto here in appointments route declaration = $encodedUserPhoto")



                        navController.navigate(route = "${BottomNavItem.DoctorDetails.screenRoute}/$doctorId/$userUsername/$encodedUserPhoto")

                    }

                )
            }
            composable(route = BottomNavItem.SignUp.screenRoute) {

                isSignInScreenOpen = false
                isSignUpScreenOpen = true

                SignUpScreen(
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
            composable(route = BottomNavItem.SignIn.screenRoute) {
                isSignInScreenOpen = true
                isSignUpScreenOpen = false


                SignInScreen(
                changeDestinationAfterLogin = {
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
                val userPhoto = backStackEntry.arguments!!.getString("userPhotoUrl")!!



                Log.d("solvingSpace8", "userPhoto here in doctorDetails route declaration = $userPhoto")




                if (!doctorUsername.isNullOrEmpty() && !userUserName.isNullOrEmpty()) {
                    isChatScreenOpen = false
                    isDoctorDetailScreenOpen = true
                    isDoctorFeedbackScreenOpen = false
                    isDoctorFeedbackScreenOpen = false
                    isNewAppointmentScreenOpen = false
                    isSignInScreenOpen = false
                    isSignUpScreenOpen = false




                    DoctorsDetailScreenLayout(
                        doctorUsername = doctorUsername,
                        userUsername = userUserName,
                        goToChatScreen = { doctorUserName, userUsername, userPhotoReceived, docPhoto, docName->
                            val userPicUrl = if(userPhotoReceived != "") Uri.encode(userPhotoReceived) else "userHasNoPhoto"
                            val encodedDoctorPhotoUrl = if(docPhoto != "")Uri.encode(docPhoto) else "docHasNoPhoto"



                            navController.navigate("${BottomNavItem.ClientDoctorChat.screenRoute}/${doctorUserName}/${userUsername}/$userPicUrl/$encodedDoctorPhotoUrl/${docName}")

                        },
                        goToReviewScreen = { doctorUserName->
                            val encodedUserPhoto = if(userPhoto != "")Uri.encode(userPhoto) else "userHasNoPhoto"

                            navController.navigate("${BottomNavItem.ReviewsAndFeedbacks.screenRoute}/${doctorUserName}/${encodedUserPhoto}")

                        },
                        goToNewAppointmentScreen = { doctorName, doctorSpecialty, clientName, clientId, doctorUserName, clientPhotoUrl ->
                            val clientPhotoEncoded = if(clientPhotoUrl != "")Uri.encode(clientPhotoUrl)else clientPhotoUrl
                            navController.navigate("${BottomNavItem.NewAppointment.screenRoute}/${doctorName}/${doctorSpecialty}/${clientName}/${clientId}/${doctorUserName}/${clientPhotoEncoded}")

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
                "${BottomNavItem.ClientDoctorChat.screenRoute}/{doctorUsername}/{userUsername}/{userPhotoUrl}/{doctorPhotoUrl}/{doctorName}",
                arguments = listOf(navArgument("doctorUsername") {
                    type = NavType.StringType
                },
                    navArgument("userUsername") {
                        type = NavType.StringType
                    },
                    navArgument("userPhotoUrl") {
                        type = NavType.StringType
                    },
                    navArgument("doctorPhotoUrl") {
                        type = NavType.StringType
                    },
                    navArgument("doctorName"){
                        type = NavType.StringType
                    })
            ) { backStackEntry ->
                val doctorUsername = backStackEntry.arguments?.getString("doctorUsername")
                val userUserName = backStackEntry.arguments?.getString("userUsername")
                val userPhotoUrl = backStackEntry.arguments!!.getString("userPhotoUrl")!!
                val docPhotoUrl = backStackEntry.arguments!!.getString("doctorPhotoUrl")!!
                val docName = backStackEntry.arguments?.getString("doctorName")






                if (!doctorUsername.isNullOrEmpty() && !userUserName.isNullOrEmpty()) {
                    isChatScreenOpen = true
                    isDoctorDetailScreenOpen = false
                    isDoctorFeedbackScreenOpen = false
                    isNewAppointmentScreenOpen = false
                    isSignInScreenOpen = false
                    isSignUpScreenOpen = false




                        Log.d("solvingSpace6", "valor de userPhotoUrl $userPhotoUrl")




                    ClientDoctorChatScreenLayout(
                        doctorUsername = doctorUsername,
                        userUsername = userUserName,
                        viewModel = hiltViewModel<ChatViewModel>(),
                        snackbarHostState = snackbarHostState,
                        userName = name,
                        goBackToDoctorDetails = {


                            val encodedUserPhoto = if(userPhotoUrl != "")Uri.encode(userPhotoUrl) else "userHasNoPhoto"

//                            navController.popBackStack(route = "${BottomNavItem.DoctorDetails.screenRoute}/$doctorUsername/$userUsername/$encodedUserPhoto",
//                                inclusive = false,
//                                saveState = false)


                            navController.popBackStack()

                            Log.d("solvingSpace8", "userPhoto here in clientDoctorChat route declaration = $encodedUserPhoto")


                            navController.navigate(route = "${BottomNavItem.DoctorDetails.screenRoute}/$doctorUsername/$userUsername/$encodedUserPhoto")


                        },
                        doctorPhotoUrl = docPhotoUrl,
                        doctorName = docName?:"",
                        userPhotoUrl = userPhotoUrl?:""

                    )

                }
            }


        }

    }
}

