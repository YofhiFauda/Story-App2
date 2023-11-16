package com.yofhi.storyapp.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yofhi.storyapp.data.repository.UserRepository
import com.yofhi.storyapp.di.UserInject
import com.yofhi.storyapp.ui.login.LoginViewModel
import com.yofhi.storyapp.ui.register.SignUpViewModel


class FactoryUserViewModel(private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: FactoryUserViewModel? = null
        fun getInstance(context: Context): FactoryUserViewModel =
            instance ?: synchronized(this) {
                instance ?: FactoryUserViewModel(UserInject.provideRepository(context))
            }.also { instance = it }
    }
}