package com.chaev.fastfin.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
@Entity(tableName ="rates")
data class RatesDb(
    @PrimaryKey
    val date: String,
    val base: String,
    val rates: Map<String, Float>
)
