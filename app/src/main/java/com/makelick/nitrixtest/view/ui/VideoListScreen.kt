package com.makelick.nitrixtest.view.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makelick.nitrixtest.R
import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.data.local.model.VideoItem
import com.makelick.nitrixtest.view.VideoListIntent
import com.makelick.nitrixtest.view.VideoListState

@Composable
fun VideoListScreen(
    modifier: Modifier = Modifier,
    state: VideoListState,
    onEvent: (VideoListIntent) -> Unit
) {
    when {
        state.isLoading -> Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }

        else -> VideosList(
            state = state,
            onEvent = onEvent
        )
    }

    AnimatedVisibility(
        visible = state.isError,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = modifier.fillMaxSize()
        ) {
            ErrorSnackbar(onEvent = onEvent)
        }
    }
}

@Composable
fun ErrorSnackbar(
    modifier: Modifier = Modifier,
    onEvent: (VideoListIntent) -> Unit = {}
) {
    Snackbar(
        modifier = modifier.padding(16.dp),
        action = {
            TextButton(onClick = { onEvent(VideoListIntent.LoadData) }) {
                Text(stringResource(R.string.retry), color = MaterialTheme.colorScheme.onError)
            }
        },
        dismissAction = {
            TextButton(onClick = { onEvent(VideoListIntent.ClearError) }) {
                Text(stringResource(R.string.dismiss), color = MaterialTheme.colorScheme.onError)
            }
        },
        containerColor = MaterialTheme.colorScheme.error
    ) {
        Text(text = stringResource(R.string.error), color = MaterialTheme.colorScheme.onError)
    }
}

@Composable
fun VideosList(
    state: VideoListState,
    modifier: Modifier = Modifier,
    onEvent: (VideoListIntent) -> Unit = {}
) {
    val lazyColumnState = rememberLazyListState()
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyColumnState
    ) {
        item {
            CategoryChips(
                categories = state.categories,
                selected = state.selectedCategories,
                onEvent = onEvent
            )
        }

        items(state.videos, key = { it.id }) { video ->
            VideoCard(video = video, onClick = {
                onEvent(VideoListIntent.SelectVideo(video))
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorSnackbar_Preview() {
    ErrorSnackbar()
}

@Preview(showBackground = true)
@Composable
private fun VideosList_Preview() {
    VideosList(
        state = VideoListState(
            categories = listOf(VideoCategory(0, "Category 1"), VideoCategory(1, "Category 2")),
            selectedCategories = emptyList(),
            videos = listOf(
                VideoItem(
                    description = "Big Buck Bunny tells the story of a giant rabbit with a heart bigger " +
                            "than himself. When one sunny day three rodents rudely harass him, something " +
                            "snaps... and the rabbit ain't no bunny anymore! In the typical cartoon " +
                            "tradition he prepares the nasty rodents a comical revenge.\n\n" +
                            "Licensed under the Creative Commons Attribution license\n" +
                            "http://www.bigbuckbunny.org",
                    sources = listOf(),
                    id = 0,
                    categoryId = 0,
                    subtitle = "By Blender Foundation",
                    thumb = "images/BigBuckBunny.jpg",
                    title = "Big Buck Bunny"
                )
            ),
            isLoading = false,
            isError = false
        )
    )
}