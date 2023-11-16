package com.yofhi.storyapp.data.dummy

import com.yofhi.storyapp.data.local.entity.Story
import com.yofhi.storyapp.data.remote.response.ListStoryItem
import com.yofhi.storyapp.data.remote.response.LoginResponse
import com.yofhi.storyapp.data.remote.response.RegisterResponse
import com.yofhi.storyapp.data.remote.response.StoryResponse
import com.yofhi.storyapp.data.remote.response.UploadResponse
import com.yofhi.storyapp.data.result.LoginResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


object DataDummy {
    fun generateDummyRegisterResponse(): RegisterResponse {
        return RegisterResponse(
            error = false,
            message = "success"
        )
    }

    fun generateDummyLoginResponse(): LoginResponse {
        val loginResult = LoginResult(
            token = "eyKlbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyQRQiOiJ1c2VyLXlqNXBjX0xBUkNfQWdLNjEiLCJpYXQiOjE2NDE3OTk5NDl9.flTYaQ7zsdYkxuyGbiXjEDXO8kuDTcI__3UjCwt6R_I"
        )

        return LoginResponse(
            loginResult = loginResult,
            error = false,
            message = "success"
        )
    }

    fun generateDummyStoryResponse(): StoryResponse {
        val stories = arrayListOf<ListStoryItem>()

        for (i in 0 until 10) {
            val story = ListStoryItem(
                "story-FvU4u0Vp2S3PMsFg",
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "2022-01-08T06:34:18.598Z",
                "Yofhi",
                "Lorem Ipsum",
                -16.002,
                -10.212
            )

            stories.add(story)
        }
        return StoryResponse(stories, false, "Stories fetched successfully")
    }

    fun generateDummyStoryList(): List<Story> {
        val stories = arrayListOf<Story>()

        for (i in 0 until 5) {
            val story = Story(
                "story-FvU4u0Vp2S3PMsFg",
                "Yofhi",
                "Lorem Ipsum",
                "https://story-api.dicoding.dev/images/stories/photos-1641645658595_dummy-pic.png",
                "2022-01-08T06:34:18.598Z",
                -16.002,
                -10.212
            )

            stories.add(story)
        }
        return stories
    }

    fun generateDummyMultipartFile(): MultipartBody.Part {
        val dummyText = "text"
        return MultipartBody.Part.create(dummyText.toRequestBody())
    }

    fun generateDummyRequestBody(): RequestBody {
        val dummyText = "text"
        return dummyText.toRequestBody()
    }

    fun generateDummyStoryUploadResponse(): UploadResponse {
        return UploadResponse(
            error = false,
            message = "success"
        )
    }
}