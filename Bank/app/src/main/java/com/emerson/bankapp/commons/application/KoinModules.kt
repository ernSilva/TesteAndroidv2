package com.emerson.bankapp.commons.application

import com.emerson.bankapp.data.dataSources.auth.AuthLocalDataSource
import com.emerson.bankapp.data.dataSources.auth.AuthRemoteDataSource
import com.emerson.bankapp.data.dataSources.statement.StatementRemoteDataSource
import com.emerson.bankapp.data.repositories.AuthRepository
import com.emerson.bankapp.data.repositories.StatementRepository
import com.emerson.bankapp.data.services.local.Database
import com.emerson.bankapp.domain.useCases.AuthUseCase
import com.emerson.bankapp.domain.useCases.StatementUseCase
import com.emerson.bankapp.presentation.auth.AuthViewModel
import com.emerson.bankapp.presentation.home.HomeViewModel
import androidx.room.Room
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object KoinModules {

    fun applicationModules() = module {
        single { RetrofitConfig.create() }
        single {
            Room
                .databaseBuilder(
                    androidApplication(),
                    Database::class.java,
                    Database.dbName
                )
                .openHelperFactory(
                    SupportFactory(SQLiteDatabase.getBytes(Database.dbPassword.toCharArray()))
                )
                .build()
        }
    }

    fun getAuthModule() = module {
        single {
            AuthUseCase(
                AuthRepository(
                    AuthLocalDataSource(get()),
                    AuthRemoteDataSource(get())
                )
            )
        }
        viewModel { AuthViewModel(get()) }
    }

    fun getHomeModule() = module {
        single {
            StatementUseCase(
                StatementRepository(
                    StatementRemoteDataSource(get())
                )
            )
        }
        viewModel { HomeViewModel(get(), get()) }
    }
}