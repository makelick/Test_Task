package com.makelick.nitrixtest.view

import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.data.local.model.VideoItem

sealed class VideoListIntent {
    data object LoadCategories : VideoListIntent()
    data class SelectCategory(val category: VideoCategory?) : VideoListIntent()
    data class SelectVideo(val video: VideoItem) : VideoListIntent()
}