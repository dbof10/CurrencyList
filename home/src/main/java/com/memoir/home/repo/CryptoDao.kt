package com.memoir.home.repo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.memoir.home.model.CurrencyInfo

@Dao
interface CryptoDao {

    @Query("SELECT * FROM crypto")
    fun getAll(): List<CurrencyInfo>

    @Insert
    fun insert(entity: List<CurrencyInfo>)

}