package com.makelick.nitrixtest.view.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makelick.nitrixtest.view.VideoListIntent
import com.makelick.nitrixtest.view.VideoViewModel

@Composable
fun VideoNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: VideoViewModel
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = "list"
    ) {
        composable("list") {
            VideoListScreen(
                state = viewModel.state,
                onEvent = {
                    when (it) {
                        is VideoListIntent.SelectVideo -> navHostController.navigate("player")
                        else -> viewModel.handleIntent(it)
                    }
                }
            )
        }
        composable("player") {
            VideoPlayerScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                video = viewModel.state.videos[0]
            )
        }
    }
}
