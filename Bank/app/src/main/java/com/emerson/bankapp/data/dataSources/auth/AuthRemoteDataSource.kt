package com.emerson.bankapp.data.dataSources.auth

import com.emerson.bankapp.data.models.auth.LoginBody
import com.emerson.bankapp.data.models.auth.LoginResponse
import com.emerson.bankapp.data.services.remote.AuthService
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import retrofit2.Retrofit

class AuthRemoteDataSource(retrofit: Retrofit) {
    private val service = retrofit.create(AuthService::class.java)

    suspend fun postLogin(loginBody: LoginBody): Response<LoginResponse> {
        return try {
            service.postLogin(loginBody)
        } catch (e: Exception) {
            Response.error(e.hashCode(), e.message.toString().toResponseBody())
        }
    }
}