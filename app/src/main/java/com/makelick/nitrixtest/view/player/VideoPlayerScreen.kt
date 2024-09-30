package com.makelick.nitrixtest.view.player

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayerScreen(
    viewModel: PlayerViewModel,
    videoId: Long,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(videoId) {
        viewModel.playVideoWhenLoaded(videoId)
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { viewContext ->
            PlayerView(viewContext).apply {
                this.player = viewModel.player
            }
        }
    )
}