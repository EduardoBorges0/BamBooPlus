package com.app.bamboo.presentation.navigation.navGraph

sealed class NavRoutes(
    val route: String
) {
    object SplashScreen : NavRoutes("splash_screen")
    object OnBoardingScreen : NavRoutes("on_boarding_screen")
    object MainScreen : NavRoutes("main")
    object InsertMedicationTimeScreen : NavRoutes("insert_medication_time_screen")
    object MedicationScreen : NavRoutes("medication_screen")
    object AppointmentScreen : NavRoutes("appointment_screen")
    object SettingsScreen : NavRoutes("settings_screen")
    object ProfileScreen : NavRoutes("profile_screen")
    object MedicationDetailScreen : NavRoutes("medicationDetails/{id}") {
        fun createRoute(id: Long) = "medicationDetails/$id"
    }
    object AppointmentDetailScreen : NavRoutes("appointment_detail_screen/{id}") {
        fun createRoute(id: Long) = "appointment_detail_screen/$id"
    }
}