package com.rubens.conectamedicina.ui.appointmentScreen

sealed class NewAppointmentEvent {
    data class AppointmentNameChanged(val value: String): NewAppointmentEvent()
    data class AppointmentAgeChanged(val value: String): NewAppointmentEvent()
    data class AppointmentDateChanged(val value: String): NewAppointmentEvent()
    data class AppointmentVisitTimeChanged(val value: String): NewAppointmentEvent()
    data class AppointmentConditionChanged(val value: String): NewAppointmentEvent()
    data class AppointmentDescriptionChanged(val value: String): NewAppointmentEvent()
    data class AppointmentChosenHourChanged(val value: String): NewAppointmentEvent()
    data class AppointmentChosenDayChanged(val value: String): NewAppointmentEvent()
    data class AppointmentChosenMonthChanged(val value: String): NewAppointmentEvent()
    data class AppointmentDoctorNameChanged(val value: String): NewAppointmentEvent()
    data class AppointmentClientNameChanged(val value: String): NewAppointmentEvent()
    data class AppointmentServiceChanged(val value: String): NewAppointmentEvent()
    data class AppointmentClientIdChanged(val value: String): NewAppointmentEvent()
    data class AppointmentDoctorIdChanged(val value: String): NewAppointmentEvent()
    data class AppointmentAppointmentDayFormattedChanged(val value: String): NewAppointmentEvent()
    data class AppointmentClientPhotoChanged(val value: String): NewAppointmentEvent()



}