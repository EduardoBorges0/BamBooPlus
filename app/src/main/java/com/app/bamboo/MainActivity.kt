package com.app.bamboo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.bamboo.presentation.viewModel.alert.NotifyViewModel
import com.app.bamboo.utils.NotifyUtils
import dagger.hilt.android.AndroidEntryPoint


@Composable
fun AppNavigation(navController: NavHostController) {

}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotifyUtils.requestNotificationPermission(this)
        setContent {
            val navController = rememberNavController()
            AppNavigation(navController)
        }
    }
}
