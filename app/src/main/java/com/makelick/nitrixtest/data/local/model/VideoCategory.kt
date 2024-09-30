package com.makelick.nitrixtest.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_category")
data class VideoCategory(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
)
