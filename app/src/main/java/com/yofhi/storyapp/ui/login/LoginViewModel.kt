package com.yofhi.storyapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yofhi.storyapp.data.repository.UserRepository
import kotlinx.coroutines.launch


class LoginViewModel(private val repo: UserRepository) : ViewModel() {

    fun setToken(token: String, isLogin: Boolean){
        viewModelScope.launch {
            repo.setToken(token, isLogin)
        }
    }

    fun getToken() : LiveData<String> {
        return repo.getToken().asLiveData()
    }

    fun login(email: String, password: String) = repo.login(email, password)
}