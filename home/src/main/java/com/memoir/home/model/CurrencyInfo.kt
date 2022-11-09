package com.memoir.home.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "crypto")
data class CurrencyInfo(@PrimaryKey val id: String, val name: String, val symbol: String)