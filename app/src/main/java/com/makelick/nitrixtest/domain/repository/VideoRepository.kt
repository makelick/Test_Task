package com.makelick.nitrixtest.domain.repository

import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.data.local.model.VideoItem

interface VideoRepository {

    suspend fun getAllVideos(): Result<List<VideoItem>>

    suspend fun getCategories(): Result<List<VideoCategory>>

    suspend fun getVideosByCategories(categories: List<VideoCategory>): Result<List<VideoItem>>
}