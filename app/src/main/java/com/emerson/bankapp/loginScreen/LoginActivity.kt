package com.emerson.bankapp.loginScreen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.emerson.bankapp.R
import com.emerson.bankapp.utils.CPFEmailTextWatcher
import com.emerson.bankapp.utils.validations.Validations
import kotlinx.android.synthetic.main.activity_login.*

interface LoginActivityInput {
    fun show(viewModel: LoginViewModel)
}

class LoginActivity  : AppCompatActivity(), LoginActivityInput {

    var TAG: String = LoginActivity::class.java.simpleName
    var output: LoginInteractorInput? = null
    var router: LoginRouter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()
        user_edit_text.addTextChangedListener(CPFEmailTextWatcher())
        onLoginClick()
        LoginConfigurator.INSTANCE.configure(this)

    }

    override fun show(viewModel: LoginViewModel){
        Log.d(TAG,viewModel.toString())
    }

    private fun onLoginClick() {
        login_button?.setOnClickListener {
            isLoading(true)
            clearErrors()
            val user = user_edit_text?.text?.toString() ?: ""
            val password = password_edit_text?.text?.toString() ?: ""
            val hasError = loginValidates(user, password)
            if(!hasError){
                output!!.login(user, password, this)
            }
        }
    }

    fun isLoading(isLoading: Boolean){
        if(isLoading){
            loginProgress.visibility = View.VISIBLE
            login_button.visibility = View.GONE
        }else{
            loginProgress.visibility = View.GONE
            login_button.visibility = View.VISIBLE
        }
    }

    private fun clearErrors() {
        user_edit_text?.error = null
        password_edit_text?.error = null
    }

    private fun loginValidates(user: String, password: String): Boolean{
        if(user.isBlank() || !Validations.isCpfValid(user) && !Validations.isEmailValid(user)){
            Log.d(TAG,user)
            user_edit_text?.error = getString(R.string.login_validation_error_user_invalid)
            isLoading(false)
            return true
        }
        if(password.isBlank()){
            Log.d(TAG,password)
            password_edit_text?.error = getString(R.string.login_validation_error_password_invalid)
            isLoading(false)
            return true
        }
        if(!Validations.hasSpecialCharacter(password)){
            Log.d(TAG,password)
            password_edit_text?.error = getString(R.string.login_validation_error_password_character_special)
            isLoading(false)
            return true
        }
        if(!Validations.hasCapitalizedLetter(password)){
            Log.d(TAG,password)
            password_edit_text?.error = getString(R.string.login_validation_error_password_capitalized_letter)
            isLoading(false)
            return true
        }
        if(!Validations.hasNumber(password)){
            Log.d(TAG,password)
            password_edit_text?.error = getString(R.string.login_validation_error_password_number)
            isLoading(false)
            return true
        }
        return false
    }
}
