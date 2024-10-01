package com.makelick.nitrixtest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.makelick.nitrixtest.data.local.converters.DatabaseListConverter
import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.data.local.model.VideoItem

@Database(entities = [VideoCategory::class, VideoItem::class], version = 1)
@TypeConverters(DatabaseListConverter::class)
abstract class VideoDatabase : RoomDatabase() {

    abstract val videoDao: VideoDao

}