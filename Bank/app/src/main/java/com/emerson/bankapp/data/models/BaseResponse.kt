package com.emerson.bankapp.data.models

import com.emerson.bankapp.data.interfaces.ResponseInterface
import retrofit2.Response
import retrofit2.http.HTTP
import java.net.HttpURLConnection
import java.net.HttpURLConnection.*

sealed class BaseResponse<out T: Any> {
    class Success<T: Any>(val data: T) : BaseResponse<T>()
    class Error(val data: ErrorResponse?): BaseResponse<Nothing>()

    companion object {
        fun <T: Any> createFrom(response: Response<T>): BaseResponse<T> {
            return try {
                response.body()?.let { body ->
                    when {
                        response.isSuccessful -> Success(body)
                        body is ResponseInterface -> Error(body.error)
                        else -> Error(ErrorResponse(response.code(), response.message()))
                    }
                } ?: Error(ErrorResponse(response.code(), response.message()))
            } catch (e: Exception) {
                Error(ErrorResponse(e.hashCode(), e.message.toString()))
            }
        }

        fun genericError(): Error {
            return Error(ErrorResponse(
                HTTP_INTERNAL_ERROR,
                "GENERIC_ERROR"
            ))
        }
    }
}