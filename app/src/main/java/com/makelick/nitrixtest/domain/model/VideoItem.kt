package com.makelick.nitrixtest.domain.model

data class VideoItem(
    val description: String,
    val sources: List<String>,
    val subtitle: String,
    val thumb: String,
    val title: String
)
