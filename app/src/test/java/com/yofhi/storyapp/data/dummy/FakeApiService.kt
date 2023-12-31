package com.yofhi.storyapp.data.dummy

import com.yofhi.storyapp.data.remote.response.LoginResponse
import com.yofhi.storyapp.data.remote.response.RegisterResponse
import com.yofhi.storyapp.data.remote.response.StoryResponse
import com.yofhi.storyapp.data.remote.response.UploadResponse
import com.yofhi.storyapp.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApiService : ApiService {
    private val dummyLoginResponse = DataDummy.generateDummyLoginResponse()
    private val dummySignupResponse = DataDummy.generateDummyRegisterResponse()
    private val dummyStories = DataDummy.generateDummyStoryResponse()
    private val dummyUploadStory = DataDummy.generateDummyStoryUploadResponse()

    override suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return dummySignupResponse
    }

    override suspend fun login(email: String, password: String): LoginResponse {
        return dummyLoginResponse
    }

    override suspend fun getStories(
        token: String,
        page: Int?,
        size: Int?,
        location: Int
    ): StoryResponse {
        return dummyStories
    }

    override suspend fun uploadStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): UploadResponse {
        return dummyUploadStory
    }
}