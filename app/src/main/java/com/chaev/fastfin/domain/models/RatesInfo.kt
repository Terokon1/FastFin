package com.chaev.fastfin.domain.models

import com.squareup.moshi.Json

data class RatesInfo(
    val date: String,
    val base: String,
    val ratesMap: Map<String, Float>
)