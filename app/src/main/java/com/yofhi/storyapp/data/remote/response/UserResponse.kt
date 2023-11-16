package com.yofhi.storyapp.data.remote.response

import com.google.gson.annotations.SerializedName
import com.yofhi.storyapp.data.model.StoryModel
import com.yofhi.storyapp.data.model.UserModel

data class UserResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("loginResult")
    val loginResult: UserModel? = null,

    @field:SerializedName("listStory")
    val listStory: ArrayList<StoryModel>? = null
)