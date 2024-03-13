package com.rubens.conectamedicina.ui.navigation

import com.rubens.conectamedicina.R

sealed class BottomNavItem(var screenRoute: String, var icon: Int) {

    object Home: BottomNavItem("home", R.drawable.baseline_home_24)
    object SearchScreen: BottomNavItem("searchScreen", R.drawable.search)
    object SignUp: BottomNavItem("signup", 0)
    object SignIn: BottomNavItem("signin", 0)
    object DoctorDetails: BottomNavItem("doctorDetails", 0)
    object ClientDoctorChat: BottomNavItem("clientDoctorChat", 0)
    object Appointments: BottomNavItem("appointments", R.drawable.appointments)
    object NewAppointment: BottomNavItem("newAppointment", 0)
    object Notification: BottomNavItem("notification", R.drawable.notification)
    object ReviewsAndFeedbacks: BottomNavItem("reviewsAndFeedbacks", 0)

}