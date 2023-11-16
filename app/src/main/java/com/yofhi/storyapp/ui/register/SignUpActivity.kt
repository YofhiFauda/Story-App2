package com.yofhi.storyapp.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.yofhi.storyapp.R
import com.yofhi.storyapp.data.result.Result
import com.yofhi.storyapp.databinding.ActivitySignUpBinding
import com.yofhi.storyapp.ui.factory.FactoryUserViewModel
import com.yofhi.storyapp.utils.animateVisibility

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var signupViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupAction()
        setupViewModel()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageViewSignup, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextViewSignup, View.ALPHA, 1f).setDuration(300)
        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(300)
        val edName = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(300)
        val edEmail = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(300)
        val edPassword = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val signupButton = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(title,nameTextView,edName, emailTextView, edEmail, passwordTextView, edPassword, signupButton)
            startDelay = 100
        }.start()
    }

    private fun setupViewModel() {
        val factoryUserVM: FactoryUserViewModel = FactoryUserViewModel.getInstance(this)
        signupViewModel = ViewModelProvider(this, factoryUserVM)[SignUpViewModel::class.java]
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString().trim()
            val email = binding.edRegisterEmail.text.toString().trim()
            val password = binding.edRegisterPassword.text.toString().trim()
            when {
                name.isEmpty() -> {
                    binding.edRegisterName.error = resources.getString(R.string.message_validation, "name")
                }
                email.isEmpty() -> {
                    binding.edRegisterEmail.error = resources.getString(R.string.message_validation, "email")
                }
                password.isEmpty() -> {
                    binding.edRegisterPassword.error = resources.getString(R.string.message_validation, "password")
                }
                else -> {
                    signupViewModel.register(name, email, password).observe(this){ result ->
                        if (result != null){
                            when(result) {
                                is Result.Loading -> {
                                    showLoading(true)
                                }
                                is Result.Success -> {
                                    showLoading(false)
                                    val user = result.data
                                    if (user.error){
                                        Toast.makeText(this@SignUpActivity, user.message, Toast.LENGTH_SHORT).show()
                                    }else{
                                        AlertDialog.Builder(this@SignUpActivity).apply {
                                            setTitle("Yeah!")
                                            setMessage("Your account successfully created!")
                                            setPositiveButton("Next") { _, _ ->
                                                finish()
                                            }
                                            create()
                                            show()
                                        }
                                    }
                                }
                                is Result.Error -> {
                                    showLoading(false)
                                    Toast.makeText(
                                        this,
                                        resources.getString(R.string.signup_error),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            edRegisterName.isEnabled = !isLoading
            edRegisterEmail.isEnabled = !isLoading
            edRegisterPassword.isEnabled = !isLoading
            signupButton.isEnabled = !isLoading

            if (isLoading) {
                viewProgressbar.animateVisibility(true)
            } else {
                viewProgressbar.animateVisibility(false)
            }
        }
    }
}