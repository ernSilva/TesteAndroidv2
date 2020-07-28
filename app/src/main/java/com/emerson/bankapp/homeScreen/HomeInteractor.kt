package com.emerson.bankapp.homeScreen

import android.content.Context
import com.emerson.bankapp.ArrayEmptyException
import com.emerson.bankapp.AuthPreferences
import com.emerson.bankapp.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


interface HomeInteractorInput {
    fun fetchHomeMetaData(request: HomeRequest?)
    fun logout(context: Context)
}

class HomeInteractor : HomeInteractorInput {
    var output: HomePresenterInput? = null

    override fun logout(context: Context) {
        AuthPreferences(context).logout(context)
    }

    override fun fetchHomeMetaData(request: HomeRequest?) {
        RetrofitClient.instance.getStatements(request?.userId!!)
            .enqueue(object: Callback<HomeResponse> {
                override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<HomeResponse>,
                    response: Response<HomeResponse>
                ) {
                    if(response.body()?.error?.code == null){
                        val homeResponse: HomeResponse? = response.body()

                        val list = homeResponse?.statementList
                        if (null == list || list.isEmpty()) {
                            throw ArrayEmptyException("Empty Statement List")
                        }
                        output?.presentHomeMetaData(homeResponse)
                    }
                }
            })
    }

    companion object {
        var TAG = HomeInteractor::class.java.simpleName
    }
}
