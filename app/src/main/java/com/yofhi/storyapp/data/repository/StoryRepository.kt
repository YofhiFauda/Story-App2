package com.yofhi.storyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.yofhi.storyapp.data.StoryRemoteMedia
import com.yofhi.storyapp.data.local.entity.Story
import com.yofhi.storyapp.data.local.room.StoryDatabase
import com.yofhi.storyapp.data.remote.response.StoryResponse
import com.yofhi.storyapp.data.remote.response.UploadResponse
import com.yofhi.storyapp.data.result.Result
import com.yofhi.storyapp.data.remote.retrofit.ApiService
import com.yofhi.storyapp.utils.wrapEspressoIdlingResource
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository (private val apiService: ApiService, private val storyDatabase: StoryDatabase){

    fun uploadStory(token: String, imageMultipart: MultipartBody.Part, desc: RequestBody, lat: RequestBody?, lon: RequestBody?): LiveData<Result<UploadResponse>> = liveData{
        emit(Result.Loading)
        try {
            val client = apiService.uploadStory("Bearer $token",imageMultipart, desc, lat, lon)
            emit(Result.Success(client))
        }catch (e : Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getStoryLocation(token: String) : LiveData<Result<StoryResponse>> = liveData{
        emit(Result.Loading)
        try {
            val client = apiService.getStories("Bearer $token", location = 1)
            emit(Result.Success(client))
        }catch (e : Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getStories(token: String): LiveData<PagingData<Story>> {
        wrapEspressoIdlingResource {
            @OptIn(ExperimentalPagingApi::class)
            return Pager(
                config = PagingConfig(
                    pageSize = 10,
                    prefetchDistance = 5, // Adjust this value based on your requirements
                    enablePlaceholders = false // Make sure placeholders are disabled
                ),
                remoteMediator = StoryRemoteMedia(storyDatabase, apiService, token),
                pagingSourceFactory = {
                    storyDatabase.storyDao().getAllStories()
                }
            ).liveData
        }
    }
}