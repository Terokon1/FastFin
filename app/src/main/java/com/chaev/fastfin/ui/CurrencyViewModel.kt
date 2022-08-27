package com.chaev.fastfin.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaev.fastfin.domain.repositories.RatesApiRepository
import com.chaev.fastfin.ui.adapter.ListItem
import com.chaev.fastfin.utils.Left
import com.chaev.fastfin.utils.Right
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class CurrencyViewModel(private val repository: RatesApiRepository) : ViewModel() {

    private val itemsList: MutableList<ListItem> = mutableListOf()
    private val _items: MutableStateFlow<List<ListItem>> = MutableStateFlow(emptyList())
    val items: StateFlow<List<ListItem>> = _items
    var pageNumber = 1

    init {
        viewModelScope.launch {
            getRates(pageNumber)
        }
    }

    suspend fun loadNextPage() {
        getRates(pageNumber)
    }

    suspend fun updateList() {
        pageNumber = 1
        itemsList.removeAll(itemsList)
        repository.clearDb()
        getRates(pageNumber)
    }

    private suspend fun getRates(page: Int): Boolean {
        return when (val r = repository.getRates(page)) {
            is Right -> {
                r.value.forEach {
                    val header = ListItem.Header(it.date)
                    val items = it.ratesMap.map { (k, v) ->
                        ListItem.Item(k, v)
                    }
                    itemsList.add(header)
                    itemsList.addAll(items)
                }
                _items.emit(itemsList)
                pageNumber += 1
                true
            }
            is Left -> {
                Log.d("zxc", r.value.toString())
                false
            }
        }
    }

}