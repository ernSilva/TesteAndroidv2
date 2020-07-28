package com.emerson.bankapp.loginScreen

import android.util.Log
import com.emerson.bankapp.AuthPreferences
import com.emerson.bankapp.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


interface LoginInteractorInput {
    fun fetchLoginMetaData(request: LoginRequest?)
    fun login(user: String, password: String, activicy: LoginActivity)
}

class LoginInteractor : LoginInteractorInput {
    var output: LoginPresenterInput? = null

    override fun fetchLoginMetaData(request: LoginRequest?) {
        Log.e(TAG, "In method fetchLoginMetaData")
        Log.d(TAG,request.toString())
        var loginResponse = null
        output!!.presentLoginMetaData(loginResponse)
    }

    override fun login(user: String,password: String, activity: LoginActivity){
        RetrofitClient.instance.login(user, password)
            .enqueue(object: Callback<LoginResponse>{
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d(TAG, "falha"+t.message)
                    (activity as LoginActivity).isLoading(false)
                }
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    Log.d(TAG, "ok"+response.body()?.userAccount.toString())
                    Log.d(TAG, response.body()?.error.toString())
                    if(response.body()?.error?.code == null){
                        Log.d(TAG, "autenticado")
                        AuthPreferences(activity.baseContext).setUserAccount(response.body()?.userAccount!!)
                        val savedUser = AuthPreferences(activity.baseContext).getUserAccount()
                        Log.d(TAG, savedUser.toString())
                        activity.isLoading(false)
                        AuthPreferences(activity.baseContext).navigateToHome(activity.baseContext)
                    }
                }
            })
    }
    companion object {
        var TAG = LoginInteractor::class.java.simpleName
    }
}
