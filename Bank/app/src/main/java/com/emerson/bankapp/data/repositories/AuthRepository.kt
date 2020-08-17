package com.emerson.bankapp.data.repositories

import com.emerson.bankapp.data.dataSources.auth.AuthLocalDataSource
import com.emerson.bankapp.data.dataSources.auth.AuthRemoteDataSource
import com.emerson.bankapp.data.models.BaseResponse
import com.emerson.bankapp.data.models.auth.LoginBody
import com.emerson.bankapp.data.models.auth.LoginResponse
import com.emerson.bankapp.data.services.local.entities.UserEntity

class AuthRepository(
    private val localDataSource: AuthLocalDataSource,
    private val remoteDataSource: AuthRemoteDataSource
) {
    suspend fun postLogin(loginBody: LoginBody): BaseResponse<LoginResponse> {
        val response = remoteDataSource.postLogin(loginBody)
        return response.body()?.userAccount?.toEntity(loginBody)?.let { entity ->
            if (response.isSuccessful) {
                localDataSource.saveUser(entity)
            }
            BaseResponse.createFrom(response)
        } ?: BaseResponse.genericError()
    }

    suspend fun getCurrentUser(): BaseResponse<UserEntity> {
        return BaseResponse.createFrom(localDataSource.getCurrentUser())
    }

    suspend fun postLogout(): BaseResponse<Boolean> {
        return BaseResponse.createFrom(localDataSource.deleteUsers())
    }
}