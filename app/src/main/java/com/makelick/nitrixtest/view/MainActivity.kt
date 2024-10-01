package com.makelick.nitrixtest.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.makelick.nitrixtest.view.ui.VideoNavHost
import com.makelick.nitrixtest.view.ui.theme.NitrixTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NitrixTestTheme {

                val navController = rememberNavController()
                val viewModel = hiltViewModel<VideoViewModel>()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    VideoNavHost(
                        modifier = Modifier.padding(innerPadding),
                        navHostController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}