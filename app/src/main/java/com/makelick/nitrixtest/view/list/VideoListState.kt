package com.makelick.nitrixtest.view.list

import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.data.local.model.VideoItem

data class VideoListState(
    val categories: List<VideoCategory> = emptyList(),
    val videos: List<VideoItem> = emptyList(),
    val selectedCategories: List<VideoCategory> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)