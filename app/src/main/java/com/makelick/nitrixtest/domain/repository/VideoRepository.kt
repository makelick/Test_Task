package com.makelick.nitrixtest.domain.repository

import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.data.local.model.VideoItem

interface VideoRepository {

    suspend fun getAllVideos(): Result<List<VideoItem>>

    suspend fun getCategories(): Result<List<VideoCategory>>

    suspend fun getVideosByCategory(category: VideoCategory): Result<List<VideoItem>>
}