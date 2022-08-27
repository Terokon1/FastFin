package com.chaev.fastfin.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.chaev.fastfin.data.database.entities.RatesDb

@Dao
interface RatesDao {
    @Query("SELECT * FROM rates WHERE date IN (:dateList)")
    suspend fun getRatesByDate(dateList: List<String>): List<RatesDb>

    @Query("DELETE FROM rates")
    suspend fun clearRates()

    @Insert
    suspend fun insertRate(rate: RatesDb)

    @Insert
    suspend fun insertRatesList(rates: List<RatesDb>)
}