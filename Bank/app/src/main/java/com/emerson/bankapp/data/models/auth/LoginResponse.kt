package com.emerson.bankapp.data.models.auth

import com.emerson.bankapp.data.models.ErrorResponse
import com.emerson.bankapp.data.interfaces.ResponseInterface

data class LoginResponse(
    val userAccount: UserAccountResponse,
    override val error: ErrorResponse? = null
) : ResponseInterface