package com.makelick.nitrixtest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_item")
data class VideoItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val description: String,
    val sources: List<String>,
    val subtitle: String,
    val thumb: String,
    val title: String
)