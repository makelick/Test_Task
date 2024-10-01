package com.makelick.nitrixtest.data.repository

import com.makelick.nitrixtest.data.local.VideoDatabase
import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.data.local.model.VideoItem
import com.makelick.nitrixtest.data.mapper.toEntity
import com.makelick.nitrixtest.data.remote.GithubApi
import com.makelick.nitrixtest.data.remote.JsonParser
import com.makelick.nitrixtest.domain.repository.VideoRepository
import javax.inject.Inject

class GithubVideoRepository @Inject constructor(
    private val remote: GithubApi,
    private val local: VideoDatabase,
    private val jsonParser: JsonParser
) : VideoRepository {

    override suspend fun getAllVideos(): Result<List<VideoItem>> {
        var localVideos = local.videoDao.getAllVideos()

        if (localVideos.isEmpty()) {
            try {
                loadFromRemoteToLocal()
            } catch (e: Exception) {
                e.printStackTrace()
                return Result.failure(e)
            }

            localVideos = local.videoDao.getAllVideos()
        }

        return Result.success(localVideos)
    }

    override suspend fun getCategories(): Result<List<VideoCategory>> {
        var localCategories = local.videoDao.getCategories()

        if (localCategories.isEmpty()) {
            try {
                loadFromRemoteToLocal()
            } catch (e: Exception) {
                e.printStackTrace()
                return Result.failure(e)
            }

            localCategories = local.videoDao.getCategories()
        }

        return Result.success(localCategories)
    }

    override suspend fun getVideosByCategories(categories: List<VideoCategory>): Result<List<VideoItem>> {
        return try {
            Result.success(local.videoDao.getVideosByCategories(categories.map { it.id }))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    private suspend fun loadFromRemoteToLocal() {
        val remoteResponse = remote.getGithubGist(GithubApi.GIST_ID)
        val dto = jsonParser.parseGistToDto(remoteResponse)

        if (dto.isNotEmpty()) {
            local.videoDao.clearVideos()
            local.videoDao.clearCategories()

            dto.forEach { category ->
                val categoryId = local.videoDao.insertCategory(category.toEntity())
                val videoEntities = category.videos.map { it.toEntity(categoryId) }

                local.videoDao.insertVideos(videoEntities)
            }
        }
    }
}