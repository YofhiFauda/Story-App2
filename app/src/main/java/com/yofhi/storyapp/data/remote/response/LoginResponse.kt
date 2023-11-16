package com.yofhi.storyapp.data.remote.response

import com.google.gson.annotations.SerializedName
import com.yofhi.storyapp.data.result.LoginResult

data class LoginResponse(

    @field:SerializedName("loginResult")
    val loginResult: LoginResult? = null,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)