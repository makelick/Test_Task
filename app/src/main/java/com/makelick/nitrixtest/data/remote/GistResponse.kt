package com.makelick.nitrixtest.data.remote

data class GistResponse(
    val files: Map<String, GistFile>
)

data class GistFile(
    val filename: String,
    val content: String
)