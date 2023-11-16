package com.yofhi.storyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yofhi.storyapp.data.local.entity.Story
import com.yofhi.storyapp.data.repository.StoryRepository
import com.yofhi.storyapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository, private val storyRepository: StoryRepository) : ViewModel() {

    fun getToken() : LiveData<String> {
        return userRepository.getToken().asLiveData()
    }

    fun isLogin() : LiveData<Boolean> {
        return userRepository.isLogin().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

    fun getStories(token: String) : LiveData<PagingData<Story>> =
        storyRepository.getStories(token).cachedIn(viewModelScope)

    fun getName(): LiveData<String> {
        return userRepository.getUserName()
    }


}