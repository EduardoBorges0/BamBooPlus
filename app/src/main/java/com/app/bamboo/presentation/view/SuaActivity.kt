package com.app.bamboo.presentation.view

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.bamboo.presentation.view.ui.theme.BamBooTheme
import com.app.bamboo.presentation.viewModel.alert.NotifyViewModel
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        )

        enableEdgeToEdge()
        setContent {
            val notifyViewModel: NotifyViewModel = hiltViewModel()
            val medicationsViewModel: MedicationsViewModel = hiltViewModel()
            val list by notifyViewModel.getBiggerToLower.collectAsState()
            medicationsViewModel.getAllMedications()

            LaunchedEffect(list) {
                list.let {
                    if (it.isNotEmpty()) {
                        notifyViewModel.showNotifications(this@SuaActivity, this@SuaActivity)
                    }
                }
            }
            BamBooTheme {
                Greeting(
                )

            }
        }
    }
}

@Composable
fun Greeting() {
    val notifyViewModel: NotifyViewModel = hiltViewModel()
    val list = notifyViewModel.getBiggerToLower.collectAsState()
    Text(
        text = list.value.toString(),
    )
}
