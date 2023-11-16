package com.yofhi.storyapp.main

import org.junit.Assert.*
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.yofhi.storyapp.JsonConv
import com.yofhi.storyapp.R
import com.yofhi.storyapp.data.remote.retrofit.ApiConfig
import com.yofhi.storyapp.ui.main.MainActivity
import com.yofhi.storyapp.utils.EspressoIdlingResources
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class MainActivityTest {

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {

        ActivityScenario.launch(MainActivity::class.java)
        mockWebServer.start(8080)
        ApiConfig.BASE_URL = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(EspressoIdlingResources.countingIdlingResource)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.countingIdlingResource)
    }

    @Test
    fun getStoriesSuccess() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConv.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockResponse)
        onView(withId(R.id.rv_stories))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(withId(R.id.rv_stories))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
    }

}