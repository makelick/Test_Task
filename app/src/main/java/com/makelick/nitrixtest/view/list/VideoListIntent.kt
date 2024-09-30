package com.makelick.nitrixtest.view.list

import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.data.local.model.VideoItem

sealed class VideoListIntent {
    data object LoadData : VideoListIntent()
    data object ClearError : VideoListIntent()
    data class SelectCategory(val category: VideoCategory?) : VideoListIntent()
    data class SelectVideo(val video: VideoItem) : VideoListIntent()
}