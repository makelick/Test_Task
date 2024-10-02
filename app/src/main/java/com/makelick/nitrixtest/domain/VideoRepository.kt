package com.makelick.nitrixtest.domain

import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.data.local.model.VideoItem

interface VideoRepository {

    suspend fun fetchRemoteData(): Result<Unit>

    suspend fun getCategories(): Result<List<VideoCategory>>

    suspend fun getVideos(selectedCategories: List<VideoCategory>): Result<List<VideoItem>>
}