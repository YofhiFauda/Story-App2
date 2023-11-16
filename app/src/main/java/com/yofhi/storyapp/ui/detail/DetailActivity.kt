package com.yofhi.storyapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.yofhi.storyapp.data.local.entity.Story
import com.yofhi.storyapp.databinding.ActivityDetailBinding
import com.yofhi.storyapp.utils.setLocalDateFormat

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Story Detail"

        @Suppress("DEPRECATION")
        val story = intent.getParcelableExtra<Story>(EXTRA_STORY)
        binding.apply {
            tvDetailName.text = story?.name
            tvCreatedAt.setLocalDateFormat(story?.createdAt.toString())
            tvDetailDescription.text = story?.description
        }
        Glide.with(this)
            .load(story?.photoUrl)
            .into(binding.ivDetailPhoto)
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}