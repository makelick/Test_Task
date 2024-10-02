package com.makelick.nitrixtest.view.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
        startDestination = NavDestinations.List.route
    ) {
        composable(NavDestinations.List.route) {
            VideoListScreen(
                state = viewModel.state,
                onEvent = {
                    when (it) {
                        is VideoListIntent.SelectVideo ->
                            navHostController.navigate("player/${it.video.id}")
                        else -> viewModel.handleIntent(it)
                    }
                }
            )
        }

        composable(
            route = "${NavDestinations.Player.route}/{videoId}",
            arguments = listOf(navArgument("videoId") { type = NavType.LongType })
        ) { backStackEntry ->
            val videoId = backStackEntry.arguments?.getLong("videoId")
            val video = viewModel.state.videos.find { it.id == videoId }
            video?.let {
                VideoPlayerScreen(video = it)
            }
        }
    }
}

enum class NavDestinations(val route: String) {
    List("list"),
    Player("player")
}
