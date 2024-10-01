package com.makelick.nitrixtest.data.mapper

import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.data.local.model.VideoItem
import com.makelick.nitrixtest.data.remote.model.VideoCategoryDto
import com.makelick.nitrixtest.data.remote.model.VideoItemDto

fun VideoItemDto.toEntity(categoryId: Long) = VideoItem(
    id = 0,
    categoryId = categoryId,
    description = description,
    sources = sources,
    subtitle = subtitle,
    thumb = thumb,
    title = title
)

fun VideoCategoryDto.toEntity() = VideoCategory(
    id = 0,
    name = name
)