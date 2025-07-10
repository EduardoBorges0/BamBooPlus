package com.app.bamboo.presentation.navigation.navGraph

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.bamboo.data.worker.quantityThreshold.alertQuantityThreshold
import com.app.bamboo.domain.alarmManager.background_tasks.everyScheduleFalse
import com.app.bamboo.presentation.navigation.BottomNavigationBar
import com.app.bamboo.presentation.navigation.ChangeTheme
import com.app.bamboo.presentation.navigation.navGraph.navComposables.NavHostComposable
import com.app.bamboo.presentation.view.ui.theme.BamBooTheme
import com.app.bamboo.presentation.viewModel.ThemeModeViewModel
import com.app.bamboo.presentation.viewModel.alert.NotifyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNavController : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            everyScheduleFalse(applicationContext)
            alertQuantityThreshold(applicationContext)
            val navController = rememberNavController()
            val notifyViewModel: NotifyViewModel = hiltViewModel()
            val themeModeViewModel: ThemeModeViewModel = hiltViewModel()
            themeModeViewModel.getThemeMode()
            val darkMode by themeModeViewModel.themeMode.collectAsState(initial = false)
            notifyViewModel.showMedicationNotifications(this, this)

            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry.value?.destination?.route


            BamBooTheme(darkTheme = darkMode) {
                Scaffold(
                    bottomBar = {
                        if (currentRoute in listOf("main", "appointments", "checkIn")) {
                            BottomNavigationBar(navController)
                        }
                    }
                ) {
                    NavHostComposable(navController, darkMode)
                    ChangeTheme(darkMode, themeModeViewModel)
                }
            }
        }
    }
}
