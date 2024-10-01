package com.makelick.nitrixtest.data.remote

import com.makelick.nitrixtest.data.remote.model.GistResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("gists/{gistId}")
    suspend fun getGithubGist(@Path("gistId") id: String): GistResponse

    companion object {
        const val BASE_URL = "https://api.github.com/"
        const val GIST_ID = "3b19447b304616f18657"
        const val FILE_NAME = "gistfile1.txt"
    }
}