package com.emerson.bankapp.commons.application

import android.app.Application
import org.koin.android.ext.android.startKoin

class BankApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin(
            this,
            listOf(
                KoinModules.getAuthModule(),
                KoinModules.getHomeModule()
            )
        )
    }

}