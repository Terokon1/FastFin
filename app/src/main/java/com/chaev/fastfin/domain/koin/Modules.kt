package com.chaev.fastfin.domain.koin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.chaev.fastfin.data.api.RetrofitBuilder
import com.chaev.fastfin.data.database.AppDatabase
import com.chaev.fastfin.data.paging.PageDataCollector
import com.chaev.fastfin.domain.repositories.RatesApiRepository
import com.chaev.fastfin.ui.CurrencyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.O)
val appModule = module {
    single { RatesApiRepository(get(), get()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "rates-db"
        )
            .build()
            .getDao()
    }
    single { RetrofitBuilder.apiService }
    single { PageDataCollector(get(), get()) }
    viewModel { CurrencyViewModel(get<RatesApiRepository>()) }
}