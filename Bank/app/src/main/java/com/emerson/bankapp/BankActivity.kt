package com.emerson.bankapp.presentation

import com.emerson.bankapp.R
import com.emerson.bankapp.presentation.auth.AuthFragment
import com.emerson.bankapp.presentation.home.HomeFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class BankActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goToAuthPage()
    }

    fun goToHomePage() = changeCurrentFragment(HomeFragment())

    fun goToAuthPage() = changeCurrentFragment(AuthFragment())

    private fun changeCurrentFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentHost, fragment)
            .commitNow()
    }
}