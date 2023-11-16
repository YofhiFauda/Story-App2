package com.yofhi.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.yofhi.storyapp.R
import com.yofhi.storyapp.data.result.Result
import com.yofhi.storyapp.databinding.ActivityLoginBinding
import com.yofhi.storyapp.ui.factory.FactoryUserViewModel
import com.yofhi.storyapp.ui.main.MainActivity
import com.yofhi.storyapp.utils.animateVisibility

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginVM: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupViewModel()
        playAnimation()
        setupView()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageViewLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextViewLogin, View.ALPHA, 1f).setDuration(100)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val edEmail = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val edPassword = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val btnLogin = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(title, emailTextView, edEmail, passwordTextView, edPassword, btnLogin)
            startDelay = 100
        }.start()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.edLoginEmail.text.toString().trim()
        val password = binding.edRegisterPassword.text.toString().trim()
        when {
            email.isEmpty() -> {
                binding.edLoginEmail.error = resources.getString(R.string.message_validation, "email")
            }
            password.isEmpty() -> {
                binding.edRegisterPassword.error = resources.getString(R.string.message_validation, "password")
            }
            else -> {
                loginVM.login(email, password).observe(this){ result ->
                    if (result != null){
                        when(result) {
                            is Result.Loading -> {
                                showLoading(true)
                            }
                            is Result.Success -> {
                                showLoading(false)
                                val user = result.data
                                if (user.error){
                                    Toast.makeText(this@LoginActivity, user.message, Toast.LENGTH_SHORT).show()
                                }else{
                                    val token = user.loginResult?.token ?: ""
                                    loginVM.setToken(token, true)
                                }
                            }
                            is Result.Error -> {
                                showLoading(false)
                                Toast.makeText(
                                    this,
                                    resources.getString(R.string.login_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupViewModel() {
        val factoryUserVM: FactoryUserViewModel = FactoryUserViewModel.getInstance(this)
        loginVM = ViewModelProvider(this, factoryUserVM)[LoginViewModel::class.java]
        loginVM.getToken().observe(this){ token ->
            if (token.isNotEmpty()){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            edLoginEmail.isEnabled = !isLoading
            edRegisterPassword.isEnabled = !isLoading
            loginButton.isEnabled = !isLoading

            if (isLoading) {
                viewProgressbar.animateVisibility(true)
            } else {
                viewProgressbar.animateVisibility(false)
            }
        }
    }
}