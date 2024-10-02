package com.makelick.nitrixtest.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.makelick.nitrixtest.view.NavDestinations.Player.VIDEO_ID_ARG
import com.makelick.nitrixtest.view.list.ListViewModel
import com.makelick.nitrixtest.view.list.VideoListIntent
import com.makelick.nitrixtest.view.list.VideoListScreen
import com.makelick.nitrixtest.view.player.PlayerViewModel
import com.makelick.nitrixtest.view.player.VideoPlayerScreen

@Composable
fun VideoNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = NavDestinations.List.route
    ) {
        composable(route = NavDestinations.List.route) {
            val viewModel = hiltViewModel<ListViewModel>()
            VideoListScreen(
                state = viewModel.state,
                onEvent = { intent ->
                    when (intent) {
                        is VideoListIntent.SelectVideo ->
                            navHostController.navigate(
                                "${NavDestinations.Player.route}/${intent.video.id}"
                            )

                        else -> viewModel.handleIntent(intent)
                    }
                }
            )
        }

        composable(
            route = "${NavDestinations.Player.route}/{$VIDEO_ID_ARG}",
            arguments = NavDestinations.Player.args
        ) { backStackEntry ->
            val videoId = backStackEntry.arguments?.getLong(VIDEO_ID_ARG) ?: return@composable

            VideoPlayerScreen(
                hiltViewModel<PlayerViewModel>(),
                videoId
            )
        }
    }
}

sealed class NavDestinations(val route: String) {

    data object List : NavDestinations("list")

    data object Player : NavDestinations("player") {
        const val VIDEO_ID_ARG = "videoId"
        val args = listOf(
            navArgument(VIDEO_ID_ARG) { type = NavType.LongType }
        )
    }
}
