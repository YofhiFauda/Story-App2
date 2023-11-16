package com.yofhi.storyapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yofhi.storyapp.data.model.StoryModel
import com.yofhi.storyapp.data.repository.Repository_Old
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapViewStoryViewModel(private val userRepository: Repository_Old): ViewModel() {
    var myLocationPermission = MutableLiveData<Boolean>()

    fun setMyLocationPermission(value: Boolean) {
        myLocationPermission.value = value
    }

    fun getMapType() : LiveData<MapType> = userRepository.getMapType()

    fun saveMapType(mapType: MapType) {
        viewModelScope.launch {
            userRepository.saveMapType(mapType)
        }
    }

    fun getMapStyle() : LiveData<MapStyle> = userRepository.getMapStyle()

    fun saveMapStyle(mapStyle: MapStyle) {
        viewModelScope.launch {
            userRepository.saveMapStyle(mapStyle)
        }
    }

    private var _userStories = MutableLiveData<ArrayList<StoryModel>>()
    val userStories: LiveData<ArrayList<StoryModel>> = _userStories

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun getUserToken() : LiveData<String> = userRepository.getUserToken()

    suspend fun getUserStoriesWithLocation(token: String) {
        val client = userRepository.getUserStoryMapView(token)

    }
}