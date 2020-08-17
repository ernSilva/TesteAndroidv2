package com.emerson.bankapp.data.services.remote

import com.emerson.bankapp.commons.application.Api
import com.emerson.bankapp.data.models.auth.LoginBody
import com.emerson.bankapp.data.models.auth.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST(Api.login)
    suspend fun postLogin(
        @Body data: LoginBody
    ): Response<LoginResponse>
}