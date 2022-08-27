package com.chaev.fastfin.data.paging

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.chaev.fastfin.data.api.ApiService
import com.chaev.fastfin.data.database.RatesDao
import com.chaev.fastfin.data.database.entities.RatesDb
import com.chaev.fastfin.data.models.RatesResponse
import com.chaev.fastfin.domain.mappers.RatesMapper
import com.chaev.fastfin.domain.models.RatesInfo
import com.chaev.fastfin.utils.Either

@RequiresApi(Build.VERSION_CODES.N)
class PageDataCollector(private val api: ApiService, private val dao: RatesDao) {
    private val accessKey = "61c3923219fd96dd44c0eccaae2f62bd"
    private val codes = "USD,RUB,UAH,JPY,BYN"


    suspend fun getRates(datesList: List<String>): Either<Exception, List<RatesInfo>> =
        Either.of {
            val localRates = getLocalRates(datesList)
            return@of if (localRates.isEmpty()) {
                val remoteRates = getRatesRemote(datesList)
                remoteRates.map { RatesMapper.fromRaw(it) }
            } else {
                val mDatesList = datesList.toMutableList()
                localRates.forEach { r ->
                    mDatesList.removeIf {
                        it == r.date
                    }
                }
                if (mDatesList.isNotEmpty()) {
                    val remoteDateList = mDatesList.toList()
                    val remoteRates = getRatesRemote(remoteDateList)
                    (localRates + remoteRates).map { RatesMapper.fromRaw(it) }
                } else {
                    localRates.map { RatesMapper.fromRaw(it) }
                }
            }
        }


    private suspend fun getRatesRemote(datesList: List<String>): List<RatesResponse> {
        val responseList = mutableListOf<RatesResponse>()
        datesList.forEach {
            responseList.add(api.getRates(it, accessKey, codes))
        }
        val cacheList = responseList.map { RatesMapper.fromRaw(it) }
        cacheRates(cacheList)
        return responseList.toList()
    }

    private suspend fun getLocalRates(datesList: List<String>): List<RatesResponse> {
        val result = dao.getRatesByDate(datesList)
        return result.map { RatesResponse(it.date, it.base, it.rates) }.asReversed()
    }

    private suspend fun cacheRates(rates: List<RatesInfo>) {
        val ratesDb = rates.map { RatesDb(it.date, it.base, it.ratesMap) }
        dao.insertRatesList(ratesDb)
    }

}