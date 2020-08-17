package com.emerson.bankapp.data.dataSources.auth

import com.emerson.bankapp.data.services.local.Database
import com.emerson.bankapp.data.services.local.entities.UserEntity
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class AuthLocalDataSource(database: Database) {
    private val userDB = database.userDao()

    suspend fun saveUser(user: UserEntity): Response<Boolean> {
        return try {
            if (!deleteUsers().isSuccessful) return Response.success(false)

            userDB.insert(user)
            Response.success(true)
        } catch (e: Exception) {
            Response.error(e.hashCode(), e.message.toString().toResponseBody())
        }
    }

    suspend fun getCurrentUser(): Response<UserEntity> {
        return try {
            userDB.selectUsers().first()?.let { user ->
                Response.success(user)
            } ?: Response.error(0, "USER NOT FOUND".toResponseBody())
        } catch (e: Exception) {
            Response.error(e.hashCode(), e.message.toString().toResponseBody())
        }
    }

    suspend fun deleteUsers(): Response<Boolean> {
        return try {
            userDB.deleteAll()
            Response.success(userDB.selectUsers().isEmpty())
        } catch (e: Exception) {
            Response.error(e.hashCode(), e.message.toString().toResponseBody())
        }
    }
}