package com.memoir.home.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.memoir.home.model.CurrencyInfo

@Database(entities = [CurrencyInfo::class], version = 1)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun currencyDao(): CryptoDao
}