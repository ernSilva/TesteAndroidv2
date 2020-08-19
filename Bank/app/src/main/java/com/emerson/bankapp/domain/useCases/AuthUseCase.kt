package com.emerson.bankapp.domain.useCases

import com.emerson.bankapp.commons.extensions.isValidCPF
import com.emerson.bankapp.commons.extensions.isValidEmail
import com.emerson.bankapp.data.repositories.AuthRepository
import com.emerson.bankapp.data.models.BaseResponse
import com.emerson.bankapp.data.models.auth.LoginBody
import com.emerson.bankapp.domain.models.UserInfo

class AuthUseCase(private val repository: AuthRepository) {

    fun validateLogin(user: String): Boolean {
        return user.run {
            isNotEmpty() && isNotBlank() &&
                    (user.isValidEmail() || user.isValidCPF())
        }
    }

    fun validatePassword(password: String): Boolean {
        val pattern = Regex("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#\$%^&+=]).*$")
        return password.run {
            isNotBlank() && isNotEmpty() && pattern.matches(this)
        }
    }

    suspend fun postLogin(user: String, password: String): BaseResponse<UserInfo> {
        return when (val response = repository.postLogin(LoginBody(user, password))) {
            is BaseResponse.Success -> {
                BaseResponse.Success(UserInfo.createFromResponse(response.data))
            }
            is BaseResponse.Error -> response
        }
    }

    suspend fun getCurrentUser(): BaseResponse<UserInfo> {
        return when (val response = repository.getCurrentUser()) {
            is BaseResponse.Success -> {
                BaseResponse.Success(UserInfo.createFromEntity(response.data))
            }
            is BaseResponse.Error -> response
        }
    }

    suspend fun postLogout(): BaseResponse<Boolean> {
        return repository.postLogout()
    }
}