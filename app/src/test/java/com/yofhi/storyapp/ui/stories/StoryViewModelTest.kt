package com.yofhi.storyapp.ui.stories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.yofhi.storyapp.data.dummy.DataDummy
import com.yofhi.storyapp.data.result.Result
import com.yofhi.storyapp.data.remote.response.RegisterResponse
import com.yofhi.storyapp.data.repository.UserRepository
import com.yofhi.storyapp.getOrAwaitValue
import com.yofhi.storyapp.ui.register.SignUpViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var registerVM: SignUpViewModel
    private val dummySignupResponse = DataDummy.generateDummyRegisterResponse()
    private val dummyName = "User"
    private val dummyEmail = "user@email.com"
    private val dummyPassword = "password"

    @Before
    fun setup() {
        registerVM = SignUpViewModel(userRepository)
    }

    @Test
    fun `when signup failed and Result Error`() {
        val signupResponse = MutableLiveData<Result<RegisterResponse>>()
        signupResponse.value = Result.Error("Error")

        Mockito.`when`(registerVM.register(dummyName, dummyEmail, dummyPassword))
            .thenReturn(signupResponse)

        val actualSignupResponse =
            registerVM.register(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()
        Mockito.verify(userRepository).register(dummyName, dummyEmail, dummyPassword)
        Assert.assertNotNull(actualSignupResponse)
        Assert.assertTrue(actualSignupResponse is Result.Error)
    }

    @Test
    fun `when signup success and Result Success`() {
        val expectedSignupResponse = MutableLiveData<Result<RegisterResponse>>()
        expectedSignupResponse.value = Result.Success(dummySignupResponse)

        Mockito.`when`(registerVM.register(dummyName, dummyEmail, dummyPassword))
            .thenReturn(expectedSignupResponse)
    }
}