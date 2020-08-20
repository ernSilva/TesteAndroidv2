package com.emerson.bankapp.presentation.auth

import com.emerson.bankapp.R
import com.emerson.bankapp.commons.extensions.ioScope
import com.emerson.bankapp.data.models.BaseResponse
import com.emerson.bankapp.domain.models.UserInfo
import com.emerson.bankapp.domain.useCases.AuthUseCase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch

class AuthViewModel(private val useCase: AuthUseCase) : ViewModel() {
    val loginError = MutableLiveData<Int>()
    val loginResult = MutableLiveData<Boolean>()
    val currentUser = MutableLiveData<UserInfo>()

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        ioScope.launch {
            when (val response = useCase.getCurrentUser()) {
                is BaseResponse.Success -> {
                    currentUser.postValue(response.data)
                }
                is BaseResponse.Error -> {
                }
            }
        }
    }

    fun postLogin(user: String, password: String) {
        when {
            !useCase.validateLogin(user) -> {
                loginError.postValue(R.string.login_error_incorrect_user)
            }
            !useCase.validatePassword(password) -> {
                loginError.postValue(R.string.login_error_incorrect_password)
            }
            else -> {
                ioScope.launch {
                    postLoginCallback(useCase.postLogin(user, password))
                }
            }
        }
    }

    private fun postLoginCallback(response: BaseResponse<UserInfo>) {
        when (response) {
            is BaseResponse.Success -> {
                loginResult.postValue(true)
            }
            is BaseResponse.Error -> {
                loginError.postValue(R.string.login_error)
            }
        }
    }


}