package com.makelick.nitrixtest.data.remote.model

data class VideoItemDto(
    val description: String,
    val sources: List<String>,
    val subtitle: String,
    val thumb: String,
    val title: String
)
