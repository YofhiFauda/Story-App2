package com.yofhi.storyapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yofhi.storyapp.data.local.entity.Story
import com.yofhi.storyapp.data.local.room.StoryDatabase
import com.yofhi.storyapp.data.remote.response.ListStoryItem
import com.yofhi.storyapp.data.remote.response.LoginResponse
import com.yofhi.storyapp.data.remote.response.RegisterResponse
import com.yofhi.storyapp.data.remote.response.StoryResponse
import com.yofhi.storyapp.data.remote.response.UploadResponse
import com.yofhi.storyapp.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.junit.After
import org.junit.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalPagingApi::class)
@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class StoryRemoteMediaTest {
    private val mockApi = FakeApiService()
    private val token =
        "eyKlbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyQRQiOiJ1c2VyLXlqNXBjX0xBUkNfQWdLNjEiLCJpYXQiOjE2NDE3OTk5NDl9.flTYaQ7zsdYkxuyGbiXjEDXO8kuDTcI__3UjCwt6R_I"
    private var mockDb: StoryDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        StoryDatabase::class.java
    ).allowMainThreadQueries().build()

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
        val remoteMediator = StoryRemoteMedia(
            mockDb,
            mockApi,
            token,
        )
        val pagingState = PagingState<Int, Story>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @After
    fun tearDown() {
        mockDb.clearAllTables()
    }
}

class FakeApiService : ApiService {

    override suspend fun register(name: String, email: String, password: String): RegisterResponse {
        throw NotImplementedError("Not implemented")
    }

    override suspend fun login(email: String, password: String): LoginResponse {
        throw NotImplementedError("Not implemented")
    }

    override suspend fun getStories(
        token: String,
        page: Int?,
        size: Int?,
        location: Int
    ): StoryResponse {
        // You can create a fake StoryResponse here for testing purposes.
        val items: MutableList<ListStoryItem> = arrayListOf()

        for (i in 0 until (size ?: 10)) {
            val storyItem = ListStoryItem(
                id = i.toString(),
                name = "Yofhi",
                description = "Lorem",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1641645658595_dummy-pic.png",
                createdAt = "2022-01-08T06:34:18.598Z",
                lon = -16.002,
                lat = -10.212
            )
            items.add(storyItem)
        }

        return StoryResponse(error = false, listStory = items, message = "Success")
    }

    override suspend fun uploadStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): UploadResponse {
        throw NotImplementedError("Not implemented")
    }
}

