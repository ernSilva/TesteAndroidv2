package com.emerson.bankapp.data.models.auth

data class LoginBody(
    val user: String,
    val password: String
)