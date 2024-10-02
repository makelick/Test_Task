package com.makelick.nitrixtest.view.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.makelick.nitrixtest.data.local.model.VideoItem
import com.makelick.nitrixtest.data.remote.GithubApi.Companion.BASE_IMAGE_URL

@Composable
fun VideoCard(
    video: VideoItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.clickable { onClick() }
    ) {
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

@Preview
@Composable
private fun VideoCard_Preview() {
    VideoCard(
        video = VideoItem(
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
    )
}