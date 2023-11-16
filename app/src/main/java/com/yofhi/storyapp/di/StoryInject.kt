package com.yofhi.storyapp.di

import android.content.Context
import com.yofhi.storyapp.data.local.room.StoryDatabase
import com.yofhi.storyapp.data.repository.StoryRepository
import com.yofhi.storyapp.data.remote.retrofit.ApiConfig

object StoryInject {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        val database = StoryDatabase.getDatabase(context)
        return StoryRepository(apiService, database)
    }
}