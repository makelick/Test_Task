package com.makelick.nitrixtest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideos(videos: List<VideoItemEntity>)

    @Query("SELECT * FROM video_item")
    fun getVideos(): List<VideoItemEntity>

    @Query("SELECT * FROM video_item WHERE id = :id")
    fun getVideoById(id: Int): VideoItemEntity

    @Query("DELETE FROM video_item")
    fun clearVideos()

}