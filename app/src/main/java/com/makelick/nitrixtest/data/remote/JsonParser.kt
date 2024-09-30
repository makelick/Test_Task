package com.makelick.nitrixtest.data.remote

import com.makelick.nitrixtest.data.remote.model.GistResponse
import com.makelick.nitrixtest.data.remote.model.VideoCategoryDto
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

class JsonParser @Inject constructor() {

    fun parseGistToDto(gistResponse: GistResponse): List<VideoCategoryDto> {
        val file = gistResponse.files[GithubApi.FILE_NAME]
            ?: throw IllegalStateException("Remote data not found")

        return parseFileContent(file.content)
    }

    private fun parseFileContent(content: String): List<VideoCategoryDto> {
        val json = "[" + content.substringAfter("[").substringBeforeLast("]") + "]"
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, VideoCategoryDto::class.java)
        val adapter: JsonAdapter<List<VideoCategoryDto>> = moshi.adapter(type)

        return adapter.fromJson(json) ?: emptyList()
    }
}