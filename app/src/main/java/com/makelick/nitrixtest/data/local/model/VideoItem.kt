package com.makelick.nitrixtest.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "video_item",
    foreignKeys = [
        ForeignKey(
            entity = VideoCategory::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = CASCADE
        )
    ]
    )
data class VideoItem(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val categoryId: Long,
    val description: String,
    val sources: List<String>,
    val subtitle: String,
    val thumb: String,
    val title: String
)