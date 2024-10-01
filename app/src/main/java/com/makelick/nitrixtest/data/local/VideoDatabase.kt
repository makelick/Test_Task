package com.makelick.nitrixtest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.data.local.model.VideoItem

@Database(entities = [VideoCategory::class, VideoItem::class], version = 1)
abstract class VideoDatabase : RoomDatabase() {

    abstract val videoDao: VideoDao

}