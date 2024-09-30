package com.makelick.nitrixtest.data.mapper

import com.makelick.nitrixtest.data.local.VideoItemEntity
import com.makelick.nitrixtest.domain.model.VideoItem

fun VideoItemEntity.toVideoItem() = VideoItem(
    description = description,
    sources = sources,
    subtitle = subtitle,
    thumb = thumb,
    title = title
)