package com.makelick.nitrixtest.domain.repositories

import com.makelick.nitrixtest.domain.model.UniqueMediaItem

interface MediaItemRepository {
    suspend fun getMediaItems(): Result<List<UniqueMediaItem>>
}