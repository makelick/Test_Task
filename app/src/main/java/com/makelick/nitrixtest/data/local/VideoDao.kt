package com.makelick.nitrixtest.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.data.local.model.VideoItem

@Dao
interface VideoDao {

    @Upsert
    suspend fun insertCategory(category: VideoCategory): Long

    @Upsert
    suspend fun insertVideos(videos: List<VideoItem>)

    @Query("SELECT * FROM video_category")
    suspend fun getCategories(): List<VideoCategory>

    @Query("SELECT * FROM video_item")
    suspend fun getAllVideos(): List<VideoItem>

    @Query("DELETE FROM video_item")
    suspend fun clearVideos()

    @Query("DELETE FROM video_category")
    suspend fun clearCategories()

    @Query("SELECT * FROM video_item WHERE categoryId IN (:categoriesIds)")
    suspend fun getVideosByCategories(categoriesIds: List<Long>): List<VideoItem>

}