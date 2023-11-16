package com.yofhi.storyapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.yofhi.storyapp.data.repository.UserRepository
import com.yofhi.storyapp.data.remote.retrofit.ApiConfig

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object UserInject {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(context.dataStore, apiService)
    }
}