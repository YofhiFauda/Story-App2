package com.yofhi.storyapp.data.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.yofhi.storyapp.CoroutineRules
import com.yofhi.storyapp.data.dummy.DataDummy
import com.yofhi.storyapp.data.dummy.FakeApiService
import com.yofhi.storyapp.data.remote.retrofit.ApiService
import com.yofhi.storyapp.data.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserRepoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutineRules()

    @Mock
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var dataStore : DataStore<Preferences>
    private lateinit var userRepository: UserRepository

    private val dummyName = "User"
    private val dummyEmail = "user@email.com"
    private val dummyPassword = "password"

    @Before
    fun setup() {
        apiService = FakeApiService()
        userRepository = UserRepository.getInstance(dataStore, apiService)
    }

    @Test
    fun `when login response Should Not Null`() = mainCoroutineRule.runBlockingTest{
        val expectedResponse = DataDummy.generateDummyLoginResponse()
        val actualResponse = apiService.login(dummyEmail, dummyPassword)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(actualResponse, expectedResponse)
    }

    @Test
    fun `when register response Should Not Null`() = mainCoroutineRule.runBlockingTest{
        val expectedResponse = DataDummy.generateDummyRegisterResponse()
        val actualResponse = apiService.register(dummyName, dummyEmail, dummyPassword)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(actualResponse, expectedResponse)
    }
}