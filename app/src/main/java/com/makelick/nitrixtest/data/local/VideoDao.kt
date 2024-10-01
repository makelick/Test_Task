package com.makelick.nitrixtest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.data.local.model.VideoItem

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: VideoCategory) : Long

    @Query("SELECT * FROM video_category")
    suspend fun getCategories(): List<VideoCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoItem>)

    @Query("SELECT * FROM video_item")
    suspend fun getAllVideos(): List<VideoItem>

    @Query("DELETE FROM video_item")
    suspend fun clearVideos()

    @Query("DELETE FROM video_category")
    suspend fun clearCategories()

    @Query("SELECT * FROM video_item WHERE categoryId IN (:categoriesIds)")
    suspend fun getVideosByCategories(categoriesIds: List<Long>): List<VideoItem>

}