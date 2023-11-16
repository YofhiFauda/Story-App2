package com.yofhi.storyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.yofhi.storyapp.data.datastore.SettingPreference
import com.yofhi.storyapp.data.local.room.StoryDatabase
import com.yofhi.storyapp.data.remote.response.StoryResponse
import com.yofhi.storyapp.data.remote.retrofit.ApiService
import com.yofhi.storyapp.ui.maps.MapStyle
import com.yofhi.storyapp.ui.maps.MapType
import com.yofhi.storyapp.utils.ApiInterceptor
import com.yofhi.storyapp.utils.AppExecutors
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository_Old(
    private val pref: SettingPreference,
    private val apiService: ApiService,
    private val userStoryDatabase: StoryDatabase,
    val appExecutors: AppExecutors
) {
    /**
     * Access data from DataStore (SettingPreference)
     */

    fun getThemeMode() : LiveData<Boolean> = pref.getThemeMode().asLiveData()
    suspend fun saveThemeMode(value: Boolean) = pref.saveThemeMode(value)

    fun getUserToken() : LiveData<String> = pref.getUserToken().asLiveData()
    suspend fun saveUserToken(value: String) = pref.saveUserToken(value)

    fun getUserName() : LiveData<String> = pref.getUserName().asLiveData()
    suspend fun saveUserName(value: String) = pref.saveUserName(value)

    fun getUserEmail() : LiveData<String> = pref.getUserEmail().asLiveData()
    suspend fun saveUserEmail(value: String) = pref.saveUserEmail(value)

    fun getIsFirstTime() : LiveData<Boolean> = pref.isFirstTime().asLiveData()
    suspend fun saveIsFirstTime(value: Boolean) = pref.saveIsFirstTime(value)

    fun getMapType() : LiveData<MapType> = pref.getMapType().asLiveData()
    suspend fun saveMapType(value: MapType) = pref.saveMapType(value)

    fun getMapStyle() : LiveData<MapStyle> = pref.getMapStyle().asLiveData()
    suspend fun saveMapStyle(value: MapStyle) = pref.saveMapStyle(value)

    suspend fun clearCache() = pref.clearCache()

    suspend fun getUserStoryMapView(token: String) : StoryResponse {
        return userStories(token).getStories(token)
    }



    private fun userStories(token: String): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor(token))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    companion object {
        @Volatile
        private var instance: Repository_Old? = null

        @JvmStatic
        fun getInstance(
            pref: SettingPreference,
            apiService: ApiService,
            userStoryDatabase: StoryDatabase,
            appExecutors: AppExecutors
        ) : Repository_Old =
            instance ?: synchronized(this) {
                instance ?: Repository_Old(pref, apiService, userStoryDatabase, appExecutors)
            }.also { instance = it }
    }

}