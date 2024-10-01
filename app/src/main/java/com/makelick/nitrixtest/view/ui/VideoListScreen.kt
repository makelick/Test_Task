package com.makelick.nitrixtest.view.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.makelick.nitrixtest.R
import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.data.local.model.VideoItem
import com.makelick.nitrixtest.data.remote.GithubApi.Companion.BASE_IMAGE_URL
import com.makelick.nitrixtest.view.VideoListIntent
import com.makelick.nitrixtest.view.VideoListState

@Composable
fun VideoListScreen(
    modifier: Modifier = Modifier,
    state: VideoListState,
    onEvent: (VideoListIntent) -> Unit
) {
    when {
        state.isError -> ErrorMessage()
        state.isLoading -> {
            Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }

        else -> {
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
    }
}

@Composable
fun ErrorMessage(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(R.string.error),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = stringResource(R.string.error),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun CategoryChips(
    categories: List<VideoCategory>,
    modifier: Modifier = Modifier,
    selected: List<VideoCategory> = emptyList(),
    onEvent: (VideoListIntent) -> Unit = {},
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.bodyLarge
        )
        LazyRow(modifier = Modifier.padding(top = 8.dp)) {
            item {
                FilterChip(
                    selected = selected.isEmpty(),
                    onClick = {
                        onEvent(VideoListIntent.SelectCategory(null))
                    },
                    label = { Text("All") },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            items(categories) { category ->
                FilterChip(
                    selected = selected.contains(category),
                    onClick = {
                        onEvent(VideoListIntent.SelectCategory(category))
                    },
                    label = { Text(category.name) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}

@Composable
fun VideoCard(
    video: VideoItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(modifier = modifier.clickable { onClick() }) {
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = BASE_IMAGE_URL + video.thumb, contentDescription = video.title,
                modifier = Modifier
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.FillWidth,
            )
            Text(
                text = video.title,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.8f),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = video.description,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Start),
                style = MaterialTheme.typography.bodyMedium
            )
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = video.subtitle,
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.labelMedium
                )
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Author",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryChips_Preview() {
    CategoryChips(
        listOf(VideoCategory(0, "Category 1"), VideoCategory(1, "Category 2"))
    )
}

@Preview
@Composable
private fun VideoCard_Preview() {
    VideoCard(
        video = VideoItem(
            description = "Big Buck Bunny tells the story of a giant rabbit with a heart bigger than himself. When one sunny day three rodents rudely harass him, something snaps... and the rabbit ain't no bunny anymore! In the typical cartoon tradition he prepares the nasty rodents a comical revenge.\\n\\nLicensed under the Creative Commons Attribution license\\nhttp://www.bigbuckbunny.org",
            sources = listOf(),
            id = 0,
            categoryId = 0,
            subtitle = "By Blender Foundation",
            thumb = "images/BigBuckBunny.jpg",
            title = "Big Buck Bunny"
        )
    )
}

