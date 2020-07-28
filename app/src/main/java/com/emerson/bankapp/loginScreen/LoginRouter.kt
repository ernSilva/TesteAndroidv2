package com.emerson.bankapp.loginScreen

import android.util.Log
import java.lang.ref.WeakReference


internal interface LoginRouterInput {
//    fun determineNextScreen(position: Int): Intent?
//    fun passDataToNextScene(position: Int, intent: Intent?)
    fun log()
}

class LoginRouter : LoginRouterInput {
    var activity: WeakReference<LoginActivity>? = null
    
    var TAG: String = LoginRouter::class.java.simpleName

    override fun log(){
        Log.d(TAG, "LoginRouter")
    }
//    @NonNull
//    override fun determineNextScreen(position: Int): Intent { //Based on the position or someother data decide what is the next scene
//        
//    }

//    override fun passDataToNextScene(
//        position: Int,
//        intent: Intent
//    ) { //Based on the position or someother data decide the data for the next scene
//        val userAccount: UserAccount = null
//        intent.putExtra("flight", userAccount)
//    }
    
}
