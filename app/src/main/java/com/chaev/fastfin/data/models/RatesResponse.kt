package com.chaev.fastfin.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class RatesResponse(
    @field:Json(name = "date")
    val date: String?,
    @field:Json(name = "base")
    val base: String?,
    @field:Json(name = "rates")
    val rates: Map<String, Float>?
)