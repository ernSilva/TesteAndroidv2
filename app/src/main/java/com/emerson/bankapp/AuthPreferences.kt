package com.emerson.bankapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import com.google.gson.Gson
import com.emerson.bankapp.homeScreen.HomeActivity
import com.emerson.bankapp.loginScreen.LoginActivity
import com.emerson.bankapp.loginScreen.UserAccount

class AuthPreferences(context: Context) {
    private val preferences: SharedPreferences
    val TAG = AuthPreferences::class.java.simpleName

    fun getUserAccount(): UserAccount{
        Log.d(TAG,"getUserAccount")
        val sUserAccount: String? = preferences.getString(KEY_ACCOUNT_NAME, null)
        if (sUserAccount != null){
            val oUserAccount: UserAccount = Gson().fromJson(sUserAccount,UserAccount::class.java)
            return oUserAccount
        }else {
            return UserAccount(null,null, null, null, null)
        }
    }

    fun setUserAccount(accountName: UserAccount) {
        Log.d(TAG,"setUserAccount")
        val jsonString = Gson().toJson(accountName)
        val editor: Editor = preferences.edit()
        editor.putString(KEY_ACCOUNT_NAME, jsonString)
        editor.commit()
    }

    fun logout(context: Context){
        val editor: Editor = preferences.edit()
        editor.putString(KEY_ACCOUNT_NAME, null)
        editor.commit()
        navigateToLogin(context)
    }

    fun navigateToHome(context: Context) {
        context.startActivity(Intent(context, HomeActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    fun navigateToLogin(context: Context){
        context.startActivity(Intent(context, LoginActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    companion object {
        private const val PREFS_NAME = "auth"
        private const val KEY_ACCOUNT_NAME = "user_account"
    }

    init {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
}