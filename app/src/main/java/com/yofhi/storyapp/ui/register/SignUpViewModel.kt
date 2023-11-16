package com.yofhi.storyapp.ui.register

import androidx.lifecycle.ViewModel
import com.yofhi.storyapp.data.repository.UserRepository

class SignUpViewModel(private val repo: UserRepository) : ViewModel() {

    fun register(name: String, email: String, password: String) = repo.register(name, email, password)
}