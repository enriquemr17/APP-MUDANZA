package com.example.appmudanza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.appmudanza.data.database.InitialData
import com.example.appmudanza.navigation.AppNavGraph
import com.example.appmudanza.ui.theme.APPMUDANZATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            APPMUDANZATheme {
                AppNavGraph()
            }
        }
    }
}
