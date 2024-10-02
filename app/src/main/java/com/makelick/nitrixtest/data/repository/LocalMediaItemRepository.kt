package com.makelick.nitrixtest.data.repository

import android.net.Uri
import androidx.media3.common.MediaItem
import com.makelick.nitrixtest.data.local.VideoDatabase
import com.makelick.nitrixtest.domain.model.UniqueMediaItem
import com.makelick.nitrixtest.domain.repositories.MediaItemRepository
import javax.inject.Inject

class LocalMediaItemRepository @Inject constructor(
    private val database: VideoDatabase
) : MediaItemRepository {

    override suspend fun getMediaItems(): Result<List<UniqueMediaItem>> {
        return try {
            val mediaItems = database.videoDao.getAllVideos().map {
                val contentUrl = it.sources.first()
                val mediaItem = MediaItem.fromUri(Uri.parse(contentUrl))
                UniqueMediaItem(it.id, mediaItem)
            }
            Result.success(mediaItems)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}