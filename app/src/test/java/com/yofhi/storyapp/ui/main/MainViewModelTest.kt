package com.yofhi.storyapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.yofhi.storyapp.CoroutineRules
import com.yofhi.storyapp.PagingDataSourceTest
import com.yofhi.storyapp.adapter.AdapterListStory
import com.yofhi.storyapp.data.dummy.DataDummy
import com.yofhi.storyapp.data.local.entity.Story
import com.yofhi.storyapp.data.repository.StoryRepository
import com.yofhi.storyapp.data.repository.UserRepository
import com.yofhi.storyapp.getOrAwaitValue
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutineRules()

    @Mock
    private lateinit var userRepository: UserRepository
    @Mock
    private lateinit var storyRepository: StoryRepository

    private val dummyToken = "azhfxrdjgchfgchjvjhfhdgcvcnv"


    @Test
    fun `set logout successfully`() = mainCoroutineRule.runBlockingTest {

        Mockito.`when`(userRepository.logout()).thenReturn(Unit)
        val mainVM = MainViewModel(userRepository, storyRepository)

        mainVM.logout()


        Mockito.verify(userRepository).logout()
    }

    @Test
    fun `get token successfully`()  = mainCoroutineRule.runBlockingTest{
        val expectedToken = MutableLiveData<String>()
        expectedToken.value = dummyToken
        Mockito.`when`(userRepository.getToken()).thenReturn(expectedToken.asFlow())
        val mainVM = MainViewModel(userRepository, storyRepository)
        val actualToken = mainVM.getToken().getOrAwaitValue()

        Mockito.verify(userRepository).getToken()
        Assert.assertNotNull(actualToken)
        Assert.assertEquals(dummyToken, actualToken)
    }

    @Test
    fun `when get list story should not be null and return data`() = mainCoroutineRule.runBlockingTest {
        val dummyStories = DataDummy.generateDummyStoryList()
        val expectedStories = MutableLiveData<PagingData<Story>>()
        expectedStories.value = PagingDataSourceTest.snapshot(dummyStories)
        Mockito.`when`(storyRepository.getStories(dummyToken)).thenReturn(expectedStories)
        val mainVM = MainViewModel(userRepository, storyRepository)
        val actualStories = mainVM.getStories(dummyToken).getOrAwaitValue()

        val noopListUpdateCallback = object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {}
            override fun onRemoved(position: Int, count: Int) {}
            override fun onMoved(fromPosition: Int, toPosition: Int) {}
            override fun onChanged(position: Int, count: Int, payload: Any?) {}
        }

        val storyDiffer = AsyncPagingDataDiffer(
            diffCallback = AdapterListStory.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainCoroutineRule.dispatcher,
            workerDispatcher = mainCoroutineRule.dispatcher,
        )

        storyDiffer.submitData(actualStories)

        advanceUntilIdle()

        Assert.assertNotNull(actualStories)
        Assert.assertEquals(dummyStories.size, storyDiffer.snapshot().size)
    }

    @Test
    fun `when loading stories successfully, ensure data is not null, correct size, and first item is as expected`() =
        mainCoroutineRule.runBlockingTest {
            // Arrange
            val dummyStories = DataDummy.generateDummyStoryList()
            val expectedStories = MutableLiveData<PagingData<Story>>()
            expectedStories.value = PagingDataSourceTest.snapshot(dummyStories)
            Mockito.`when`(storyRepository.getStories(dummyToken)).thenReturn(expectedStories)
            val mainVM = MainViewModel(userRepository, storyRepository)

            // Act
            val actualStories = mainVM.getStories(dummyToken).getOrAwaitValue()

            // Assert
            val noopListUpdateCallback = object : ListUpdateCallback {
                override fun onInserted(position: Int, count: Int) {}
                override fun onRemoved(position: Int, count: Int) {}
                override fun onMoved(fromPosition: Int, toPosition: Int) {}
                override fun onChanged(position: Int, count: Int, payload: Any?) {}
            }

            val storyDiffer = AsyncPagingDataDiffer(
                diffCallback = AdapterListStory.DIFF_CALLBACK,
                updateCallback = noopListUpdateCallback,
                mainDispatcher = mainCoroutineRule.dispatcher,
                workerDispatcher = mainCoroutineRule.dispatcher,
            )

            storyDiffer.submitData(actualStories)

            advanceUntilIdle()

            // Assert
            assertNotNull(actualStories)
            assertEquals(dummyStories.size, storyDiffer.snapshot().size)
            assertEquals(dummyStories.first(), storyDiffer.snapshot().items.first())
        }

    @Test
    fun `when no stories available, ensure returned list is empty`() = mainCoroutineRule.runBlockingTest {
        // Arrange
        val expectedEmptyList = MutableLiveData<PagingData<Story>>()
        expectedEmptyList.value = PagingDataSourceTest.snapshot(emptyList())
        Mockito.`when`(storyRepository.getStories(dummyToken)).thenReturn(expectedEmptyList)
        val mainVM = MainViewModel(userRepository, storyRepository)

        // Act
        val actualEmptyList = mainVM.getStories(dummyToken).getOrAwaitValue()

        // Assert
        val noopListUpdateCallback = object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {}
            override fun onRemoved(position: Int, count: Int) {}
            override fun onMoved(fromPosition: Int, toPosition: Int) {}
            override fun onChanged(position: Int, count: Int, payload: Any?) {}
        }

        val emptyListDiffer = AsyncPagingDataDiffer(
            diffCallback = AdapterListStory.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainCoroutineRule.dispatcher,
            workerDispatcher = mainCoroutineRule.dispatcher,
        )

        emptyListDiffer.submitData(actualEmptyList)

        advanceUntilIdle()

        // Assert
        assertNotNull(actualEmptyList)
        assertEquals(0, emptyListDiffer.snapshot().size)
    }

    @Test
    fun `get session login successfully`() = mainCoroutineRule.runBlockingTest {
        val dummySession = true
        val expectedSession = MutableLiveData<Boolean>()
        expectedSession.value = dummySession
        Mockito.`when`(userRepository.isLogin()).thenReturn(expectedSession.asFlow())
        val mainVM = MainViewModel(userRepository, storyRepository)
        val actualSession = mainVM.isLogin().getOrAwaitValue()

        Mockito.verify(userRepository).isLogin()
        Assert.assertNotNull(actualSession)
        Assert.assertEquals(dummySession, actualSession)
    }
}
