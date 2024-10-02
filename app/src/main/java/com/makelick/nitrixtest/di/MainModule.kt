package com.makelick.nitrixtest.di

import android.content.Context
import androidx.room.Room
import com.makelick.nitrixtest.data.local.VideoDatabase
import com.makelick.nitrixtest.data.remote.GithubApi
import com.makelick.nitrixtest.data.remote.JsonParser
import com.makelick.nitrixtest.data.repository.GithubVideoRepository
import com.makelick.nitrixtest.domain.repository.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    @Singleton
    fun provideGithubApi(): GithubApi {
        return Retrofit.Builder()
            .baseUrl(GithubApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideVideoDatabase(@ApplicationContext context: Context): VideoDatabase {
        return Room.databaseBuilder(
            context,
            VideoDatabase::class.java,
            "video_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideVideoRepository(
        githubApi: GithubApi,
        videoDatabase: VideoDatabase,
        jsonParser: JsonParser
    ): VideoRepository {
        return GithubVideoRepository(githubApi, videoDatabase, jsonParser)
    }

}