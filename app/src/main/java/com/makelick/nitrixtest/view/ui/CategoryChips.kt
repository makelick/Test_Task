package com.makelick.nitrixtest.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makelick.nitrixtest.R
import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.view.VideoListIntent

@Composable
fun CategoryChips(
    categories: List<VideoCategory>,
    modifier: Modifier = Modifier,
    selected: List<VideoCategory> = emptyList(),
    onEvent: (VideoListIntent) -> Unit = {},
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.categories),
            style = MaterialTheme.typography.bodyLarge
        )
        LazyRow {
            item {
                FilterChip(
                    selected = selected.isEmpty(),
                    onClick = {
                        onEvent(VideoListIntent.SelectCategory(null))
                    },
                    label = { Text(stringResource(R.string.all)) },
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

@Preview(showBackground = true)
@Composable
private fun CategoryChips_Preview() {
    CategoryChips(
        listOf(VideoCategory(0, "Category 1"), VideoCategory(1, "Category 2"))
    )
}