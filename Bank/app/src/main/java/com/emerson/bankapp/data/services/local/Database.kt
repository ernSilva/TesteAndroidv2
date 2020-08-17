package com.emerson.bankapp.data.services.local

import com.emerson.bankapp.data.services.local.daos.UserDao
import com.emerson.bankapp.data.services.local.entities.UserEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val dbName = "bankDatabase"
        const val dbPassword = "DBPassword"
    }
}