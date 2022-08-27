package com.chaev.fastfin.domain.mappers

import com.chaev.fastfin.data.models.RatesResponse
import com.chaev.fastfin.domain.exceptions.MappingException
import com.chaev.fastfin.domain.models.RatesInfo

object RatesMapper {
    fun fromRaw(r: RatesResponse) = RatesInfo(
        r.date ?: throw MappingException("No date"),
        r.base ?: throw MappingException("No base"),
        r.rates ?: throw MappingException("No rates"),
    )
}