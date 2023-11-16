package com.yofhi.storyapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.yofhi.storyapp.CoroutineRules
import com.yofhi.storyapp.data.dummy.DataDummy
import com.yofhi.storyapp.data.result.Result
import com.yofhi.storyapp.data.remote.response.LoginResponse
import com.yofhi.storyapp.data.repository.UserRepository
import com.yofhi.storyapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class LoginViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var loginVM: LoginViewModel
    private val dummyLoginResponse = DataDummy.generateDummyLoginResponse()
    private val dummyToken = "azhfxrdjgchfgchjvjhfhdgcvcnv"
    private val dummyEmail = "user@email.com"
    private val dummyPassword = "password"

    @Before
    fun setup() {
        loginVM = LoginViewModel(userRepository)
    }

    @get:Rule
    var mainCoroutineRule = CoroutineRules()

    @Test
    fun `when signup failed and Result Error`() {
        val loginResponse = MutableLiveData<Result<LoginResponse>>()
        loginResponse.value = Result.Error("Error")

        Mockito.`when`(loginVM.login(dummyEmail, dummyPassword)).thenReturn(loginResponse)

        val actualLoginResponse = loginVM.login(dummyEmail, dummyPassword).getOrAwaitValue()
        Mockito.verify(userRepository).login(dummyEmail, dummyPassword)
        Assert.assertNotNull(actualLoginResponse)
        Assert.assertTrue(actualLoginResponse is Result.Error)
    }

    @Test
    fun `when login success and Result Success`() {
        val expectedLoginResponse = MutableLiveData<Result<LoginResponse>>()
        expectedLoginResponse.value = Result.Success(dummyLoginResponse)

        Mockito.`when`(loginVM.login(dummyEmail, dummyPassword))
            .thenReturn(expectedLoginResponse)

        val actualLoginResponse = loginVM.login(dummyEmail, dummyPassword).getOrAwaitValue()
        Mockito.verify(userRepository).login(dummyEmail, dummyPassword)
        Assert.assertNotNull(actualLoginResponse)
        Assert.assertTrue(actualLoginResponse is Result.Success)
        Assert.assertSame(dummyLoginResponse, (actualLoginResponse as Result.Success).data)
    }

    @Test
    fun `Save token successfully`() = mainCoroutineRule.runBlockingTest {
        loginVM.setToken(dummyToken, true)
        Mockito.verify(userRepository).setToken(dummyToken, true)
    }
}