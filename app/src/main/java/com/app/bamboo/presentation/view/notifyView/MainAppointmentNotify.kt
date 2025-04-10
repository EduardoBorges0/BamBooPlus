package com.app.bamboo.presentation.view.notifyView

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.bamboo.presentation.view.notifyView.ui.theme.BamBooTheme
import com.app.bamboo.presentation.view.utils.notify.MainNotifyView

class MainAppointmentNotify : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val intent = intent.getStringExtra("id").toString()
            Log.d("ID", "ESSE É O ID $intent")
            BamBooTheme {
//                MainNotifyView(
//                    title = "",
//                    description = "",
//                    time = "",
//                    confirmClick = {},
//                    cancelClick = {}
//                )
            }
        }
    }
}
