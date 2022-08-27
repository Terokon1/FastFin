package com.chaev.fastfin.data.database.typeConverters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import java.lang.reflect.Type

class MapJsonConverter {
    @TypeConverter
    fun toJson(map: Map<String, Float>): String {
        val jsonAdapter = getAdapter()
        return jsonAdapter.toJson(map)
    }

    @TypeConverter
    fun toMap(json: String): Map<String, Float> {
        val jsonAdapter = getAdapter()
        return jsonAdapter.fromJson(json) ?: mapOf()
    }

    private fun getAdapter(): JsonAdapter<Map<String, Float>> {
        val moshi = Moshi.Builder().build()
        val type =
            Types.newParameterizedType(Map::class.java, String::class.java, Float::class.javaObjectType)
        return moshi.adapter<Map<String, Float>>(type)
    }
}