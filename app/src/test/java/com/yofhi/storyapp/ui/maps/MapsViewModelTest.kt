package com.yofhi.storyapp.ui.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.yofhi.storyapp.data.dummy.DataDummy
import com.yofhi.storyapp.data.remote.response.StoryResponse
import com.yofhi.storyapp.data.repository.StoryRepository
import com.yofhi.storyapp.data.result.Result
import com.yofhi.storyapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storiesRepository: StoryRepository
    private lateinit var mapsVM: MapsViewModel
    private val dummyToken = "azhfxrdjgchfgchjvjhfhdgcvcnv"
    private val dummyStory = DataDummy.generateDummyStoryResponse()

    @Before
    fun setUp() {
        mapsVM = MapsViewModel(storiesRepository)
    }

    @Test
    fun `when Network error Should Return Error`() = runBlocking {
        // Create a LiveData to hold the expected result
        val expectedStories = MutableLiveData<Result<StoryResponse>>()
        expectedStories.value = Result.Error("Error")

        // Use a Mockito stub to return the expected result
        Mockito.`when`(storiesRepository.getStoryLocation(dummyToken)).thenReturn(expectedStories)

        // Call the function to be tested
        val actualStories: Result<StoryResponse> = mapsVM.getStories(dummyToken).getOrAwaitValue()

        // Verify the function calls and check the result
        Mockito.verify(storiesRepository).getStoryLocation(dummyToken)
        Assert.assertNotNull(actualStories)
        Assert.assertTrue(actualStories is Result.Error)
    }

    @Test
    fun `when Get maps story Should Not Be Null and Return Success`() = runBlocking {
        val expectedStories = MutableLiveData<Result<StoryResponse>>()
        expectedStories.value = Result.Success(dummyStory)

        Mockito.`when`(storiesRepository.getStoryLocation(dummyToken)).thenReturn(expectedStories)

        val actualResult: Result<StoryResponse> = mapsVM.getStories(dummyToken).getOrAwaitValue()

        Mockito.verify(storiesRepository).getStoryLocation(dummyToken)
        Assert.assertNotNull(actualResult)
        Assert.assertTrue(actualResult is Result.Success<*>)

        val expectedListSize = dummyStory.listStory.size
        val actualListSize = (actualResult as Result.Success<StoryResponse>).data.listStory.size
        Assert.assertEquals(expectedListSize, actualListSize)
    }
}
