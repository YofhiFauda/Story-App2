package com.yofhi.storyapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.yofhi.storyapp.R
import com.yofhi.storyapp.ui.main.MainActivity


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var topAnimation: Animation
    private lateinit var buttomAnimation: Animation
    private lateinit var imageLogo: ImageView

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        buttomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        imageLogo = findViewById(R.id.img_splash_screen)

        imageLogo.startAnimation(topAnimation)
    }
}