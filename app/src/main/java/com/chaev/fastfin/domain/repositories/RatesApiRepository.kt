package com.chaev.fastfin.domain.repositories

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.chaev.fastfin.data.database.RatesDao
import com.chaev.fastfin.data.models.RatesResponse
import com.chaev.fastfin.data.paging.PageDataCollector
import com.chaev.fastfin.domain.models.RatesInfo
import com.chaev.fastfin.utils.Either
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class RatesApiRepository(private val pager: PageDataCollector, private val dao: RatesDao) {

    private val currentDate =
        LocalDate.now()


    suspend fun getRates(page: Int): Either<Exception, List<RatesInfo>> {
        val itemsAmount = 5
        val startDate = currentDate.minusDays((itemsAmount * (page - 1).toLong()))
        val datesList = mutableListOf<String>()
        for (i in 0 until itemsAmount) {
            datesList.add(
                startDate.minusDays(i.toLong())
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            )
        }
        return pager.getRates(datesList)

    }

    suspend fun clearDb(){
        dao.clearRates()
    }
}