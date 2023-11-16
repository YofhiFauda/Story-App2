package com.yofhi.storyapp.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yofhi.storyapp.R
import com.yofhi.storyapp.adapter.AdapterListStory
import com.yofhi.storyapp.adapter.LoadingStateAdapter
import com.yofhi.storyapp.databinding.ActivityMainBinding
import com.yofhi.storyapp.ui.factory.FactoryStoryViewModel
import com.yofhi.storyapp.ui.maps.MapsActivity
import com.yofhi.storyapp.ui.onboard.OnBoardingActivity
import com.yofhi.storyapp.ui.story.StoryActivity

//val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainVM: MainViewModel
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvStories.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvStories.layoutManager = LinearLayoutManager(this)
        }


        title = "Dashboard"
        setupViewModel()
        setupAction()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupViewModel() {
        val factoryStoryVM: FactoryStoryViewModel = FactoryStoryViewModel.getInstance(this)
        mainVM = ViewModelProvider(this, factoryStoryVM)[MainViewModel::class.java]

        mainVM.isLogin().observe(this) {
            if (!it) {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            }
        }

        mainVM.getToken().observe(this) { token ->
            this.token = token
            if (token.isNotEmpty()) {
                val adapter = AdapterListStory()
                binding.rvStories.adapter = adapter.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        adapter.retry()
                    }
                )
                mainVM.getStories(token).observe( this) { pagingData ->
                    Log.i("SubmitData", "New data received: $pagingData")
                    adapter.submitData(lifecycle, pagingData)
                    adapter.notifyDataSetChanged()
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.item_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                mainVM.logout()
                true
            }

            R.id.map_menu -> {
                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra(MapsActivity.EXTRA_TOKEN, token)
                startActivity(intent)
                true
            }

            R.id.setting -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }

            else -> true
        }
    }
    private fun setupAction() {
        binding.fabAddStories.setOnClickListener {
            val intent = Intent(this, StoryActivity::class.java)
            intent.putExtra(StoryActivity.EXTRA_TOKEN, token)
            startActivity(intent)
        }
    }
}