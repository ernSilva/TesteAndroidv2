package com.emerson.bankapp

import com.emerson.bankapp.homeScreen.HomeResponse
import com.emerson.bankapp.loginScreen.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("user") user: String,
        @Field("password") password: String
    ): Call<LoginResponse>


    @GET("statements/{userId}")
    fun getStatements(@Path("userId") userId: Int): Call<HomeResponse>

}
