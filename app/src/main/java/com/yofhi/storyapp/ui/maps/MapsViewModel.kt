package com.yofhi.storyapp.ui.maps

import androidx.lifecycle.ViewModel
import com.yofhi.storyapp.data.repository.StoryRepository


class MapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getStories(token: String) = storyRepository.getStoryLocation(token)
}