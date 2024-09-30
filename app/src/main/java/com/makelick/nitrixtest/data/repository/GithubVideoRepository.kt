package com.makelick.nitrixtest.data.repository

import coil.network.HttpException
import com.makelick.nitrixtest.data.local.VideoDatabase
import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.data.local.model.VideoItem
import com.makelick.nitrixtest.data.mapper.toEntity
import com.makelick.nitrixtest.data.remote.GithubApi
import com.makelick.nitrixtest.data.remote.JsonParser
import com.makelick.nitrixtest.domain.repositories.VideoRepository
import java.io.IOException
import javax.inject.Inject

class GithubVideoRepository @Inject constructor(
    private val remote: GithubApi,
    private val local: VideoDatabase,
    private val jsonParser: JsonParser
) : VideoRepository {

    override suspend fun fetchRemoteData(): Result<Unit> {
        try {
            val remoteResponse = remote.getGithubGist(GithubApi.GIST_ID)
            val dto = jsonParser.parseGistToDto(remoteResponse)

            local.videoDao.clearCategories()
            local.videoDao.clearVideos()

            dto.forEach { category ->
                val categoryId = local.videoDao.insertCategory(category.toEntity())
                val videoEntities = category.videos.map { it.toEntity(categoryId) }

                local.videoDao.insertVideos(videoEntities)
            }
            return Result.success(Unit)

        } catch (e: Exception) {
            if (e is IOException || e is IllegalStateException || e is HttpException) {
                e.printStackTrace()
                return Result.failure(e)
            } else throw e
        }
    }

    override suspend fun getCategories(): Result<List<VideoCategory>> {
        return Result.success(local.videoDao.getCategories())
    }

    override suspend fun getVideos(
        selectedCategories: List<VideoCategory>
    ): Result<List<VideoItem>> {
        return if (selectedCategories.isEmpty()) {
            Result.success(local.videoDao.getAllVideos())
        } else try {
            Result.success(local.videoDao.getVideosByCategories(selectedCategories.map { it.id }))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}