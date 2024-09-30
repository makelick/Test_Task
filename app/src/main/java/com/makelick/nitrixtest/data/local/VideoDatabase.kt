package com.makelick.nitrixtest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VideoItemEntity::class], version = 1)
abstract class VideoDatabase : RoomDatabase() {

    abstract val videoDao: VideoDao

}