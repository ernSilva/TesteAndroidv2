package com.emerson.bankapp.domain.models

import com.emerson.bankapp.commons.extensions.formatBrCurrency
import com.emerson.bankapp.data.models.auth.LoginResponse
import com.emerson.bankapp.data.services.local.entities.UserEntity

data class UserInfo(
    val id: Int,
    val name: String,
    val bankAccount: String,
    val agency: String,
    val balance: Float,
    val login: String = String(),
    val password: String = String()
) {
    fun getAccountNumber(): String {
        return "$bankAccount / ${getAgencyFormatted()}"
    }

    private fun getAgencyFormatted(): String {
        return agency.run {
            "${substring(0, 2)}.${substring(2, 8)}-${substring(8)}"
        }
    }

    fun getBalanceFormatted(): String {
        return balance.formatBrCurrency()
    }

    companion object {
        fun createFromResponse(loginResponse: LoginResponse): UserInfo {
            return UserInfo(
                loginResponse.userAccount.userId,
                loginResponse.userAccount.name,
                loginResponse.userAccount.bankAccount,
                loginResponse.userAccount.agency,
                loginResponse.userAccount.balance
            )
        }

        fun createFromEntity(userEntity: UserEntity): UserInfo {
            return UserInfo(
                userEntity.userId,
                userEntity.name,
                userEntity.bankAccount,
                userEntity.agency,
                userEntity.balance,
                userEntity.login,
                userEntity.password
            )
        }
    }
}