package com.chaev.fastfin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chaev.fastfin.data.database.entities.RatesDb
import com.chaev.fastfin.data.database.typeConverters.MapJsonConverter

@Database(entities = [RatesDb::class], version = 1)
@TypeConverters(MapJsonConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao() : RatesDao

}