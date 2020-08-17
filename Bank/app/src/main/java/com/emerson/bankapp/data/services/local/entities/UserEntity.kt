package com.emerson.bankapp.data.services.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val USER_TABLE_NAME = "users"

@Entity(tableName = USER_TABLE_NAME)
class UserEntity(
    @PrimaryKey
    val userId: Int,
    val name: String,
    val bankAccount: String,
    val agency: String,
    val balance: Float,
    val login: String,
    val password: String
)